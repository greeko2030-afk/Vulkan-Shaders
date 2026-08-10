#version 450

// Data received from VulkanUniformManager (Java)
layout(push_constant) uniform ShaderConfig {
    int volumetricClouds;
    int waterCaustics;
    float eyeAdaptationIntensity;
    float colorTemperature;
} config;

layout(location = 0) in vec2 inUV;
layout(location = 1) in vec3 inNormal;
layout(location = 2) in vec3 inWorldPos;

layout(location = 0) out vec4 outFragColor;

// Helper function: Convert Color Temperature (Kelvin) to RGB
vec3 kelvinToRGB(float temperature) {
    vec3 color = vec3(1.0);
    // Simplified temperature math for example purposes
    color.r = temperature > 6500.0 ? (12000.0 - temperature) / 5500.0 : 1.0;
    color.b = temperature < 6500.0 ? temperature / 6500.0 : 1.0;
    color.g = (color.r + color.b) * 0.5;
    return clamp(color, 0.0, 1.0);
}

void main() {
    vec3 baseColor = vec3(0.5, 0.7, 1.0); // Base Sky/Scene Color
    
    // Apply Color Temperature
    vec3 tempColor = kelvinToRGB(config.colorTemperature);
    baseColor *= tempColor;
    
    // Apply Eye Adaptation (Simulating Brightness Adjustment)
    // Intensity controls how strong the adaptation effect is
    float exposure = mix(1.0, 1.5, config.eyeAdaptationIntensity);
    baseColor = vec3(1.0) - exp(-baseColor * exposure); // Tone mapping
    
    // Apply Water Caustics
    if (config.waterCaustics == 1) {
        float causticPattern = sin(inWorldPos.x * 10.0) * cos(inWorldPos.z * 10.0);
        baseColor += vec3(causticPattern * 0.2); 
    }
    
    outFragColor = vec4(baseColor, 1.0);
}

