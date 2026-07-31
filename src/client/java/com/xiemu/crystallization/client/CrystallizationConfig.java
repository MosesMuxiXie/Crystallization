package com.xiemu.crystallization.client;

import com.xiemu.crystallization.Crystallization;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public final class CrystallizationConfig {
	private static final Path CONFIG_FILE = FabricLoader.getInstance()
			.getConfigDir()
			.resolve("crystallization.properties");

	private static boolean enabled;

	private CrystallizationConfig() {
	}

	public static void load() {
		enabled = false;
		if (!Files.isRegularFile(CONFIG_FILE)) {
			return;
		}

		Properties properties = new Properties();
		try (InputStream input = Files.newInputStream(CONFIG_FILE)) {
			properties.load(input);
			enabled = Boolean.parseBoolean(properties.getProperty("enabled", "false"));
		} catch (IOException exception) {
			Crystallization.LOGGER.warn("Could not load {}", CONFIG_FILE, exception);
		}
	}

	public static boolean isEnabled() {
		return enabled;
	}

	public static void setEnabled(boolean value) {
		if (enabled == value) {
			return;
		}
		enabled = value;
		save();
	}

	private static void save() {
		Properties properties = new Properties();
		properties.setProperty("enabled", Boolean.toString(enabled));

		try {
			Files.createDirectories(CONFIG_FILE.getParent());
			try (OutputStream output = Files.newOutputStream(CONFIG_FILE)) {
				properties.store(output, "Crystallization client settings");
			}
		} catch (IOException exception) {
			Crystallization.LOGGER.warn("Could not save {}", CONFIG_FILE, exception);
		}
	}
}
