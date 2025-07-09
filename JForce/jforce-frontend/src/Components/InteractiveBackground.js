import React from 'react';
import Particles from 'react-tsparticles';
import { loadStarsPreset } from 'tsparticles-preset-stars';

function InteractiveBackground() {
    const particlesInit = async (engine) => {
        await loadStarsPreset(engine);
    };

    return (
        <Particles
            id="tsparticles"
            init={particlesInit}
            options={{
                preset: 'stars',
                fullScreen: { enable: true },
                background: {
                    color: { value: "#42047e" }
                }
            }}
        />
    );
}

export default InteractiveBackground;
