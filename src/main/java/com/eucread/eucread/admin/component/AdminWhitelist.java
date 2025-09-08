package com.eucread.eucread.admin.component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.eucread.eucread.admin.exception.AdminErrorCode;
import com.eucread.eucread.exception.BusinessException;

import jakarta.annotation.PostConstruct;

@Component
public class AdminWhitelist {

	private final Set<String> whitelist = new HashSet<>();

	@PostConstruct
	public void init() {
		try {
			Path path = Path.of("src/main/resources/admin/whitelist.txt");
			List<String> lines = Files.readAllLines(path);
			for (String line : lines) {
				if (!line.isBlank()) {
					whitelist.add(line.trim().toLowerCase());
				}
			}
		} catch (IOException e) {
			throw new BusinessException(AdminErrorCode.CANNOT_READ_WHITELIST);
		}
	}

	public boolean contains(String email) {
		return whitelist.contains(email.toLowerCase());
	}
}
