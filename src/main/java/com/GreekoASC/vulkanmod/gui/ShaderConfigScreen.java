package com.com.greeko.vulkanmod.gui;

import com.greeko.vulkanmod.config.ShaderSettings;
import com.greeko.vulkanmod.shader.VulkanUniformManager;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.network.chat.Component;

public class ShaderConfigScreen extends Screen {
    private final Screen parent;
    private final ShaderSettings settings;

    public ShaderConfigScreen(Screen parent) {
        super(Component.literal("Vulkan Shader - Advanced Settings"));
        this.parent = parent;
        this.settings = ShaderSettings.getInstance();
    }

    @Override
    protected void init() {
        int startY = 40;
        int btnWidth = 150;
        int btnHeight = 20;
        int centerX = this.width / 2;

        // --- Toggle Button (Example) ---
        this.addRenderableWidget(Button.builder(
            Component.literal("Volumetric Clouds: " + (settings.volumetricClouds ? "ON" : "OFF")),
            button -> {
                settings.volumetricClouds = !settings.volumetricClouds;
                button.setMessage(Component.literal("Volumetric Clouds: " + (settings.volumetricClouds ? "ON" : "OFF")));
                VulkanUniformManager.updateUniforms();
            }
        ).bounds(centerX - 160, startY, btnWidth, btnHeight).build());


        // --- Eye Adaptation Slider (0.0 to 1.0) ---
        this.addRenderableWidget(new AbstractSliderButton(centerX + 10, startY, btnWidth, btnHeight, Component.empty(), settings.eyeAdaptationIntensity) {
            {
                this.updateMessage();
            }

            @Override
            protected void updateMessage() {
                this.setMessage(Component.literal(String.format("Eye Adaptation: %.2f", this.value)));
            }

            @Override
            protected void applyValue() {
                settings.eyeAdaptationIntensity = (float) this.value;
                VulkanUniformManager.updateUniforms();
            }
        });


        // --- Color Temperature Slider (1000K to 12000K) ---
        // Minecraft sliders only use 0.0 to 1.0 internally, so we map it to our Kelvin range
        double minTemp = 1000.0;
        double maxTemp = 12000.0;
        double currentSliderValue = (settings.colorTemperature - minTemp) / (maxTemp - minTemp);

        this.addRenderableWidget(new AbstractSliderButton(centerX - 160, startY + 25, btnWidth, btnHeight, Component.empty(), currentSliderValue) {
            {
                this.updateMessage();
            }

            @Override
            protected void updateMessage() {
                double kelvin = minTemp + (this.value * (maxTemp - minTemp));
                this.setMessage(Component.literal(String.format("Color Temp: %.0f K", kelvin)));
            }

            @Override
            protected void applyValue() {
                settings.colorTemperature = (float) (minTemp + (this.value * (maxTemp - minTemp)));
                VulkanUniformManager.updateUniforms();
            }
        });


        // --- Done Button ---
        this.addRenderableWidget(Button.builder(Component.literal("Apply & Close"), button -> {
            this.minecraft.setScreen(this.parent);
        })
        .bounds(centerX - 100, this.height - 30, 200, 20)
        .build());
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        super.render(guiGraphics, mouseX, mouseY, delta);
        guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 15, 0xFFFFFF);
    }
}
