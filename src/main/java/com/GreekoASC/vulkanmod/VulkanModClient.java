package com.com.greeko.vulkanmod;

import com.com.greeko.vulkanmod.shader.ShaderManager;
import net.fabricmc.api.ClientModInitializer;

public class VulkanModClient implements ClientModInitializer {
    
    @Override
    public void onInitializeClient() {
        // Extract default shaders when the client starts
        ShaderManager.initDefaultShader();
        
        // Note: You would also register your Keybindings and Command registrations here
        // VulkanModClientKeys.registerKeys();
        // VulkanShaderErrorCommand.register();
        
        System.out.println("[VulkanMod] Client Initialized - Vulkan SPIR-V Shader support ready!");
    }
}

