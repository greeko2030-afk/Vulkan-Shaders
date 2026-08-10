package com.yourname.vulkanmod.config;

public class ShaderSettings {
    // Boolean Toggles
    public boolean volumetricClouds = true;
    public boolean waterCaustics = true;
    public boolean softDynamicShadows = true;
    public boolean rainAtmosphere = true;
    
    // Slider Values (Float)
    public float eyeAdaptationIntensity = 0.5f; // Range: 0.0 to 1.0
    public float colorTemperature = 6500.0f;    // Range: 1000.0 to 12000.0 (Kelvin)

    private static ShaderSettings instance = new ShaderSettings();

    public static ShaderSettings getInstance() {
        return instance;
    }
}

