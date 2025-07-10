import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import ParticlesBackground from '../Components/ParticlesBackground';
import { FaUser, FaLock, FaFacebookF, FaTwitter, FaGoogle, FaLinkedin, FaEye, FaEyeSlash } from 'react-icons/fa';
import './LoginPage.css';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const LoginPage = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [showPassword, setShowPassword] = useState(false);
    const [error, setError] = useState('');

    const navigate = useNavigate();

    const handleLogin = async () => {
        console.log("Login clicked"); // TEST amaçlı ekle

        try {
            const response = await axios.post('http://localhost:8080/auth/login', {
                username,
                password,
            });

            console.log("Response:", response.data);

            const token = response.data.token;
            const role = response.data.role;

            localStorage.setItem("token", token);
            localStorage.setItem("role", role);
            localStorage.setItem("username", username);

            if(role === "Human_Resources") {
                navigate('/HumanResources-Dashboard');
            } else if(role === "Admin") {
                navigate('/Admin-Dashboard');
            } else if(role === "Inventory_Manager") {
                navigate('/InventoryManager-Dashboard');
            }

        } catch (err) {
            console.error(err); // detaylı hata logu
            toast.error("User not Found!");
        }
    };
    const handleForgotPassword = async () => {
        if (!username) {
            setError('Please enter your username before requesting a reset.');
            return;
        }

        try {
            await axios.post('http://localhost:8080/auth/forgot-password', null, {
                params: { username }
            });

            toast.success("A password reset link has been sent to your email.");
        } catch (error) {
            toast.error("User not found or email sending failed.");
        }
    };

    return (
        <div className="login-container">
            <ParticlesBackground />
            <div className="login-box">
                <h2>Welcome To The JForce Inventory Controller</h2>
                <p>Please Login To The System</p>

                <div className="input-group">
                    <FaUser className="icon" />
                    <input
                        type="text"
                        placeholder="Username"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                    />
                </div>

                <div className="input-group">
                    <FaLock className="icon" />
                    <input
                        type={showPassword ? 'text' : 'password'}
                        placeholder="Password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                    />
                    <span className="eye-icon" onClick={() => setShowPassword(!showPassword)}>
            {showPassword ? <FaEyeSlash /> : <FaEye />}
          </span>
                </div>

                {error && <p className="error-text">{error}</p>}

                <p className="forgot" onClick={handleForgotPassword} style={{ cursor: 'pointer', color: '#00f' }}>
                    Forgot password?
                </p>



                <button className="login-button" onClick={handleLogin}>LOGIN</button>

                <div className="social-login">
                    <a href="https://www.facebook.com/jforcebilisim" target="_blank" rel="noreferrer"><FaFacebookF /></a>
                    <a href="https://x.com/jforcebilisim" target="_blank" rel="noreferrer"><FaTwitter /></a>
                    <a href="https://jforce.com.tr/" target="_blank" rel="noreferrer"><FaGoogle /></a>
                    <a href="https://www.linkedin.com/company/jforce/" target="_blank" rel="noreferrer"><FaLinkedin /></a>
                </div>
            </div>
        </div>
    );
};

export default LoginPage;
