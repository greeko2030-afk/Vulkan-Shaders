package com.com.greeko.vulkanmod.shader;

import net.minecraft.client.Minecraft;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class ShaderManager {
    private static final String DEFAULT_SHADER = "Vulkan Shader Basic";

    public static void initDefaultShader() {
        File gameDir = Minecraft.getInstance().gameDirectory;
        Path shaderPacksDir = gameDir.toPath().resolve("shaderpacks");
        Path defaultShaderPath = shaderPacksDir.resolve(DEFAULT_SHADER);
        Path shadersDir = defaultShaderPath.resolve("shaders");

        // If the shader pack does not exist, extract it from the jar
        if (!Files.exists(defaultShaderPath)) {
            try {
                Files.createDirectories(shadersDir);
                
                // Extract configuration and properties
                extractResource("default_shaders/" + DEFAULT_SHADER + "/pack.properties", defaultShaderPath.resolve("pack.properties"));
                extractResource("default_shaders/" + DEFAULT_SHADER + "/shader_settings.json", defaultShaderPath.resolve("shader_settings.json"));
                
                // Extract GLSL source files (for developers)
                extractResource("default_shaders/" + DEFAULT_SHADER + "/shaders/core.frag.glsl", shadersDir.resolve("core.frag.glsl"));
                extractResource("default_shaders/" + DEFAULT_SHADER + "/shaders/core.vert.glsl", shadersDir.resolve("core.vert.glsl"));
                
                // Extract compiled SPV binary files (for Vulkan pipeline)
                extractResource("default_shaders/" + DEFAULT_SHADER + "/shaders/fragment.spv", shadersDir.resolve("fragment.spv"));
                extractResource("default_shaders/" + DEFAULT_SHADER + "/shaders/vertex.spv", shadersDir.resolve("vertex.spv"));
                
                System.out.println("[VulkanMod] Successfully extracted default shaders.");
            } catch (Exception e) {
                System.err.println("[VulkanMod] Failed to extract default shaders: " + e.getMessage());
            }
        }
    }

    private static void extractResource(String resourcePath, Path targetPath) throws Exception {
        try (InputStream in = ShaderManager.class.getClassLoader().getResourceAsStream(resourcePath)) {
            if (in == null) {
                throw new Exception("Resource not found in mod jar: " + resourcePath);
            }
            Files.copy(in, targetPath, StandardCopyOption.REPLACE_EXISTING);
        }
    }
}

