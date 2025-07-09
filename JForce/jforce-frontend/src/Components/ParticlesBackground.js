import React from 'react';
import Particles from 'react-tsparticles';
import { loadStarsPreset } from 'tsparticles-preset-stars';


const ParticlesBackground = () => {
    const customInit = async (engine) => {
        await loadStarsPreset(engine);
    };

    const options = {
        preset: 'stars',
        background: {
            color: '#5405cc',
        },
        particles: {
            move: {
                enable: true,
                speed: 0.5,
                direction: "none",
                outModes: "bounce",
            },
        },
        interactivity: {
            events: {
                onHover: {
                    enable: true,
                    mode: 'bubble',
                },
                resize: true,
            },
            modes: {
                bubble: {
                    distance: 120,
                    duration: 2,
                    size: 6,
                    opacity: 0.8,
                },
            },
        },
    };

    return <Particles init={customInit} options={options} />;
};

export default ParticlesBackground;
