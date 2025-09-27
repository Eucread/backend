package com.eucread.eucread.comic.series.service;

import com.eucread.eucread.comic.episode.entity.Episode;
import com.eucread.eucread.comic.episode.repository.EpisodeRepository;

import com.eucread.eucread.comic.series.dto.SeriesCreateRequestDto;
import com.eucread.eucread.comic.series.dto.SeriesDetailViewResponseDto;
import com.eucread.eucread.comic.series.dto.SeriesResponseDto;
import com.eucread.eucread.comic.series.dto.SeriesStatusUpdateRequestDto;
import com.eucread.eucread.comic.series.entity.Series;
import com.eucread.eucread.comic.series.entity.SeriesCreator;
import com.eucread.eucread.comic.series.enums.SeriesStatus;
import com.eucread.eucread.comic.series.repository.SeriesCreatorRepository;
import com.eucread.eucread.comic.series.repository.SeriesRepository;
import com.eucread.eucread.profile.creator.entity.CreatorProfile;
import com.eucread.eucread.profile.creator.repository.CreatorRepository;
import com.eucread.eucread.users.entity.User;
import com.eucread.eucread.users.entity.UserRole;
import com.eucread.eucread.users.enums.Role;
import com.eucread.eucread.users.repository.UserRoleRepository;
import com.eucread.eucread.util.security.SecurityUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SeriesService {
    private final SecurityUtil securityUtil;
    private final UserRoleRepository userRoleRepository;
    private final SeriesRepository seriesRepository;
    private final CreatorRepository creatorRepository;
    private final SeriesCreatorRepository seriesCreatorRepository;
    private final EpisodeRepository episodeRepository;

    // 1. 시리즈 등록
    public void createSeries(SeriesCreateRequestDto seriesCreateRequestDto) {
        User currentUser = securityUtil.getCurrentUser();
        UserRole userRole = userRoleRepository.findByUserAndRole(currentUser, Role.CREATOR).orElseThrow(() -> new EntityNotFoundException("not found"));

        Series series = Series.builder()
                .title(seriesCreateRequestDto.getTitle())
                .genre(seriesCreateRequestDto.getGenre())
                .thumbnail(seriesCreateRequestDto.getThumbnail())
                .status(SeriesStatus.DRAFT)
                .introduction(seriesCreateRequestDto.getIntroduction())
//                .ageRating()
//                .score(0)
                .build();

        seriesRepository.save(series);

        CreatorProfile creatorProfile = creatorRepository.findCreatorProfileByUser(currentUser).orElseThrow(() -> new EntityNotFoundException("not found"));
        SeriesCreator seriesCreator = SeriesCreator.builder()
                .creator(creatorProfile)
                .series(series)
                .build();

        seriesCreatorRepository.save(seriesCreator);
    }

    // 2. 시리즈 삭제
    public void deleteSeries(Long id) {
        User currentUser = securityUtil.getCurrentUser();
        UserRole userRole = userRoleRepository.findByUserAndRole(currentUser, Role.CREATOR).orElseThrow(() -> new EntityNotFoundException("not found"));
        // 이 아이디에 맞는 시리즈인지 예외처리하는 로직이 필요할까?

        seriesRepository.deleteById(id);
    }


    // 3. 시리즈 수정
    public void patchSeries(SeriesCreateRequestDto seriesCreateRequestDto, Long id) {
        User currentUser = securityUtil.getCurrentUser();
        UserRole userRole = userRoleRepository.findByUserAndRole(currentUser, Role.CREATOR).orElseThrow(() -> new EntityNotFoundException("not found"));

        Series series = seriesRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("not found"));
        series.update(seriesCreateRequestDto);
        seriesRepository.save(series);
    }

    public void updateSeriesStatus(SeriesStatusUpdateRequestDto seriesStatusUpdateRequestDto) {
        User currentUser = securityUtil.getCurrentUser();
        UserRole userRole = userRoleRepository.findByUserAndRole(currentUser, Role.ADMIN).orElseThrow(() -> new EntityNotFoundException("not found"));
        Series series = seriesRepository.findById(seriesStatusUpdateRequestDto.getSeriesId()).orElseThrow(() -> new EntityNotFoundException("not found"));
        series.updateStatus(seriesStatusUpdateRequestDto.getStatus());
        seriesRepository.save(series);
    }

    public SeriesDetailViewResponseDto getSeriesById(Long id) {
        Series series = seriesRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        List<Episode> episodeList = episodeRepository.findEpisodeBySeries(series);
        List<SeriesDetailViewResponseDto.EpisodeDto> episodeDtoList = new ArrayList<>();

        for (Episode episode : episodeList) {
            SeriesDetailViewResponseDto.EpisodeDto episodeDto = SeriesDetailViewResponseDto.EpisodeDto.builder()
                    .episodeId(episode.getEpisodeId())
                    .number(episode.getNumber())
                    .title(episode.getTitle())
                    .averageStar(episode.getAverageStar())
                    .build();

            episodeDtoList.add(episodeDto);
        }
        SeriesCreator seriesCreator = seriesCreatorRepository.findSeriesCreatorBySeries(series).orElseThrow(EntityNotFoundException::new);


        SeriesDetailViewResponseDto seriesDetailViewResponseDto = SeriesDetailViewResponseDto.builder()
                .seriesId(series.getId())
                .author(seriesCreator.getCreator().getUser().getUsername())  // 펜네임이 뭔지 모르겠슨
                .genre(series.getGenre())
                .title(series.getThumbnail())
                .thumbnail(series.getThumbnail())
                .score(series.getScore())
                .episodeDtoList(episodeDtoList)
                .build();

        return seriesDetailViewResponseDto;
    }

    public List<SeriesResponseDto> getPublishedSeries(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Series> series = seriesRepository.findByStatus(SeriesStatus.PUBLISHED, pageable);
        List<SeriesResponseDto> seriesResponseDtoList = new ArrayList<>();

        for (Series s : series.getContent()) {
            SeriesResponseDto seriesResponseDto = SeriesResponseDto.builder()
                    .id(s.getId())
                    .title(s.getTitle())
                    .thumbnail(s.getThumbnail())
                    .score(s.getScore())
                    .build();

            seriesResponseDtoList.add(seriesResponseDto);
        }

        return seriesResponseDtoList;
    }

    // 4. 모든 시리즈 조회

    // 5. 시리즈 상세보기

    // 6. 시리즈 관리자 승인

}
