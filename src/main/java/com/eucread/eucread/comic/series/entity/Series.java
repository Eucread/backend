package com.eucread.eucread.comic.series.entity;

import java.util.ArrayList;
import java.util.List;

import com.eucread.eucread.comic.episode.entity.Episode;
import com.eucread.eucread.comic.series.dto.SeriesCreateRequestDto;
import com.eucread.eucread.comic.series.enums.SeriesStatus;
import com.eucread.eucread.util.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Series extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;

	private String genre;

	@Lob
	@Column(columnDefinition = "TEXT")
	private String thumbnail;

	@Lob
	@Column(columnDefinition = "TEXT")
	private String introduction;

	@Enumerated(EnumType.STRING)
	private SeriesStatus status;

	private String ageRating;

	private double score;

	@OneToMany(mappedBy = "series", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<SeriesCreator> seriesCreators = new ArrayList<>();

	@OneToMany(mappedBy = "series", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Episode> episodes = new ArrayList<>();


	public void update(SeriesCreateRequestDto seriesCreateRequestDto) {
		this.title = seriesCreateRequestDto.getTitle();
		this.genre = seriesCreateRequestDto.getGenre();
		this.thumbnail = seriesCreateRequestDto.getThumbnail();
		this.introduction = seriesCreateRequestDto.getIntroduction();
	}

	public void updateStatus(SeriesStatus seriesStatus) {
		this.status = seriesStatus;
	}
}
