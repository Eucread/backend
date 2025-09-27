package com.eucread.eucread.comic.episode.service;

import com.eucread.eucread.comic.episode.dto.EpisodeCreateRequestDto;
import com.eucread.eucread.comic.episode.dto.EpisodeDetailViewResponseDto;
import com.eucread.eucread.comic.episode.dto.EpisodeUpdateRequestDto;
import com.eucread.eucread.comic.episode.dto.UpdateEpisodeStatusRequestDto;
import com.eucread.eucread.comic.episode.entity.Episode;
import com.eucread.eucread.comic.episode.enums.EpisodeStatus;
import com.eucread.eucread.comic.episode.repository.EpisodeRepository;
import com.eucread.eucread.comic.page.entity.Page;
import com.eucread.eucread.comic.page.entity.PageRepository;
import com.eucread.eucread.comic.series.entity.Series;
import com.eucread.eucread.comic.series.repository.SeriesRepository;
import com.eucread.eucread.users.entity.User;
import com.eucread.eucread.users.entity.UserRole;
import com.eucread.eucread.users.enums.Role;
import com.eucread.eucread.users.repository.UserRoleRepository;
import com.eucread.eucread.util.BaseEntity;
import com.eucread.eucread.util.security.SecurityUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EpisodeService {
    private final SeriesRepository seriesRepository;
    private final EpisodeRepository episodeRepository;
    private final SecurityUtil securityUtil;
    private final UserRoleRepository userRoleRepository;
    private final PageRepository pageRepository;

    public void createEpisode(Long id, EpisodeCreateRequestDto episodeCreateRequestDto) {
        Series series = seriesRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        User currentUser = securityUtil.getCurrentUser();
        UserRole userRole = userRoleRepository.findByUserAndRole(currentUser, Role.CREATOR).orElseThrow(EntityNotFoundException::new);

        LocalDateTime now = LocalDateTime.now();

        Episode episode = Episode.builder()
                .series(series)
                .number(episodeRepository.countBySeries(series) + 1)
                .title(episodeCreateRequestDto.getTitle())
                .scheduledAt(now)
                .status(EpisodeStatus.DRAFT)
//                .publishedAt() // 뭔지 모르겠음
                .views(0L)
                .thumbnail(episodeCreateRequestDto.getThumbnail())
                .averageStar(0.0) // 최초에는 0으로 설정
                .build();

        episodeRepository.save(episode);

        List<EpisodeCreateRequestDto.PageDto> pageDtos = episodeCreateRequestDto.getPages();
        for (EpisodeCreateRequestDto.PageDto pageDto : pageDtos) {
            Page<BaseEntity> page = Page.builder()
                    .episode(episode)
                    .storageKey(pageDto.getStorageKey())
                    .width(pageDto.getWidth())
                    .height(pageDto.getHeight())
                    .pageNo(pageDto.getPageNo())
                    .build();

            pageRepository.save(page);

        }
    }

    public void deleteEpisode(Long episodeId) {
        Episode episode = episodeRepository.findById(episodeId).orElseThrow(EntityNotFoundException::new);
        pageRepository.deletePageByEpisode(episode);
        episodeRepository.deleteById(episodeId);
    }

    public void updateEpisodeStatus(UpdateEpisodeStatusRequestDto updateEpisodeStatusRequestDto) {
        User currentUser = securityUtil.getCurrentUser();
        UserRole userRole = userRoleRepository.findByUserAndRole(currentUser, Role.ADMIN).orElseThrow(EntityNotFoundException::new);
        Episode episode = episodeRepository.findById(updateEpisodeStatusRequestDto.getEpisodeId()).orElseThrow(EntityNotFoundException::new);

        episode.setStatus(updateEpisodeStatusRequestDto.getEpisodeStatus());
        episodeRepository.save(episode);
    }

    public EpisodeDetailViewResponseDto viewEpisode(Long episodeId) {
        Episode episode = episodeRepository.findById(episodeId).orElseThrow(EntityNotFoundException::new);
        List<Page> pageList = pageRepository.finByEpisode(episode);
        List<EpisodeDetailViewResponseDto.Pages> pages = new ArrayList<>();
        for (Page page : pageList) {
            EpisodeDetailViewResponseDto.Pages p = EpisodeDetailViewResponseDto.Pages.builder()
                    .number(page.getPageNo())
                    .storageKey(page.getStorageKey())
                    .height(page.getHeight())
                    .width(page.getWidth())
                    .build();
            pages.add(p);
        }


        EpisodeDetailViewResponseDto episodeDetailViewResponseDto = EpisodeDetailViewResponseDto.builder()
                .id(episodeId)
                .title(episode.getTitle())
                .number(episode.getNumber())
                .pages(pages)
                .build();

        return episodeDetailViewResponseDto;
    }

    public void updateEpisode(EpisodeUpdateRequestDto episodeUpdateRequestDto) {
        Episode episode = episodeRepository.findById(episodeUpdateRequestDto.getId()).orElseThrow(EntityNotFoundException::new);

        episode.setTitle(episodeUpdateRequestDto.getTitle());
        episode.setThumbnail(episodeUpdateRequestDto.getThumbnail());

        for (EpisodeCreateRequestDto.PageDto p : episodeUpdateRequestDto.getPages()) {
            Optional<Page> isPagePresent = pageRepository.findByEpisodeAndPageNo(episode, p.getPageNo());
            if (isPagePresent == null) {
                Page page = Page.builder()
                        .pageNo(p.getPageNo())
                        .storageKey(p.getStorageKey())
                        .height(p.getHeight())
                        .width(p.getWidth())
                        .build();

                pageRepository.save(page);
            } else {
                Page page = isPagePresent.get();
                page.setPageNo(p.getPageNo());
                page.setStorageKey(p.getStorageKey());
                page.setHeight(p.getHeight());
                page.setWidth(p.getWidth());
                pageRepository.save(page);
            }
        }
    }
}
