#version 450

// Input attributes from Minecraft vertex buffer
layout(location = 0) in vec3 inPosition;
layout(location = 1) in vec2 inUV;
layout(location = 2) in vec3 inNormal;

// Push constants or uniform buffers for Matrix transformations
layout(push_constant) uniform Matrices {
    mat4 modelViewMatrix;
    mat4 projectionMatrix;
} matrices;

// Output variables sent to the Fragment Shader (core.frag.glsl)
layout(location = 0) out vec2 outUV;
layout(location = 1) out vec3 outNormal;
layout(location = 2) out vec3 outWorldPos;

void main() {
    // Pass data to fragment shader
    outUV = inUV;
    outNormal = inNormal;
    
    // Simplified world position calculation
    outWorldPos = inPosition; 
    
    // Calculate final screen position of the vertex
    gl_Position = matrices.projectionMatrix * matrices.modelViewMatrix * vec4(inPosition, 1.0);
}
