import React, {useState} from 'react';
import ParticlesBackground from '../Components/ParticlesBackground';
import {FaUser, FaLock, FaFacebookF, FaTwitter, FaGoogle, FaLinkedin} from 'react-icons/fa';
import { FaEye, FaEyeSlash } from 'react-icons/fa';
import './LoginPage.css';

const LoginPage = () => {
    const [showPassword, setShowPassword] = useState(false);
    return (
        <div className="login-container">
            <ParticlesBackground />
            <div className="login-box">
                <h2>Welcome To The JForce Inventory Controller</h2>
                <p>Please Login To The System</p>
                <div className="input-group">
                    <FaUser className="icon" />
                    <input type="text" placeholder="Username" />
                </div>

                <div className="input-group">
                    <FaLock className="icon" />
                    <input
                        type={showPassword ? 'text' : 'password'}
                        placeholder="Password"
                        required
                    />
                    <span className="eye-icon" onClick={() => setShowPassword(!showPassword)}>
    {showPassword ? <FaEyeSlash /> : <FaEye />}
  </span>
                </div>

                <p className="forgot">Forgot password?</p>

                <button className="login-button">LOGIN</button>

                <div className="social-login">
                    <a href="https://www.facebook.com/jforcebilisim" target="_blank" rel="noopener noreferrer">
                        <FaFacebookF />
                    </a>
                    <a href="https://x.com/jforcebilisim" target="_blank" rel="noopener noreferrer">
                        <FaTwitter />
                    </a>
                    <a href="https://jforce.com.tr/" target="_blank" rel="noopener noreferrer">
                        <FaGoogle />
                    </a>
                    <a href="https://www.linkedin.com/company/jforce/" target="_blank" rel="noopener noreferrer">
                        <FaLinkedin />
                    </a>
                </div>
            </div>
        </div>
    );
};

export default LoginPage;
