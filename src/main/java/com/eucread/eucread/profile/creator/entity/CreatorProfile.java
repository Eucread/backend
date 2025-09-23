package com.eucread.eucread.profile.creator.entity;

import java.util.ArrayList;
import java.util.List;

import com.eucread.eucread.comic.series.entity.SeriesCreator;
import com.eucread.eucread.users.entity.User;
import com.eucread.eucread.util.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatorProfile extends BaseEntity {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Setter
	private String penName;

	@Setter
	private String introduction;

	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;

	@OneToMany(mappedBy = "creator", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<SeriesCreator> seriesCreators = new ArrayList<>();
}
