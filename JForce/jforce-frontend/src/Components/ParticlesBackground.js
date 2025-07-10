import React from 'react';
import Particles from 'react-tsparticles';
import { loadStarsPreset } from 'tsparticles-preset-stars';

const ParticlesBackground = () => {
    const customInit = async (engine) => {
        await loadStarsPreset(engine);
    };

    const options = {
        preset: 'stars',
        fpsLimit: 30,
        background: {
            color: '#5405cc',
        },
        particles: {
            move: {
                enable: true,
                speed: 0.02,
                direction: "none",
                outModes: "bounce",
            },
        },
        reduceMotion: {
            enable: true,
            reduce: true
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

    return (
        <div className="particles-background">
            <Particles init={customInit} options={options} />
        </div>
    );};

export default ParticlesBackground;
