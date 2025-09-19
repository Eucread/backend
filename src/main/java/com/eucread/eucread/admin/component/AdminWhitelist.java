package com.eucread.eucread.admin.component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.eucread.eucread.admin.exception.AdminErrorCode;
import com.eucread.eucread.exception.BusinessException;

import jakarta.annotation.PostConstruct;

@Component
public class AdminWhitelist {

	@Value("classpath:admin/whitelist.txt")
	private Resource whitelistResource;

	private final Set<String> whitelist = new HashSet<>();

	@PostConstruct
	public void init() {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(whitelistResource.getInputStream()))) {
			String line;
			while ((line = reader.readLine()) != null) {
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
