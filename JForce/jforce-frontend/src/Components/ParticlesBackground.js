import React from 'react';
import Particles from 'react-tsparticles';
import { loadLinksPreset } from 'tsparticles-preset-links';

const ParticlesBackground = () => {
    const customInit = async (engine) => {
        await loadLinksPreset(engine);
    };

    const options = {
        preset: 'links',
        background: {
            color: '#0d47a1', // Dilersen kendi renginle değiştir
        },
        particles: {
            color: {
                value: "#ffffff",
            },
            links: {
                color: "#ffffff",
                distance: 150,
                enable: true,
                opacity: 0.4,
                width: 1,
            },
            move: {
                enable: true,
                speed: 1,
                direction: "none",
                outModes: {
                    default: "bounce",
                },
            },
            number: {
                value: 80,
                density: {
                    enable: true,
                    area: 800,
                },
            },
            opacity: {
                value: 0.5,
            },
            shape: {
                type: "circle",
            },
            size: {
                value: { min: 1, max: 5 },
            },
        },
        interactivity: {
            events: {
                onHover: {
                    enable: true,
                    mode: "grab",
                },
                resize: true,
            },
            modes: {
                grab: {
                    distance: 140,
                    links: {
                        opacity: 1,
                    },
                },
            },
        },
        detectRetina: true,
    };

    return (
        <div className="particles-background">
            <Particles init={customInit} options={options} />
        </div>
    );
};

export default ParticlesBackground;
