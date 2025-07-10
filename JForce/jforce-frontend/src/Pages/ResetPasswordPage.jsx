import React, { useState } from 'react';
import axios from 'axios';
import { useSearchParams, useNavigate } from 'react-router-dom';
import { toast, ToastContainer } from 'react-toastify';
import ParticlesBackground from '../Components/ParticlesBackground';
import './LoginPage.css';
import { FaUser, FaLock, FaFacebookF, FaTwitter, FaGoogle, FaLinkedin, FaEye, FaEyeSlash } from 'react-icons/fa';


const ResetPasswordPage = () => {
    const [params] = useSearchParams();
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const navigate = useNavigate();

    const token = params.get("token");

    const handleReset = async () => {
        if (password !== confirmPassword) {
            toast.error("Passwords do not match.");
            return;
        }

        try {
            await axios.post('http://localhost:8080/auth/reset-password', {
                token,
                newPassword: password
            });
            toast.success("Password reset successful! Redirecting...");
            setTimeout(() => navigate('/'), 2500);
        } catch (e) {
            toast.error("Reset failed. Invalid or expired link.");
        }
    };

    return (
        <div className="login-container">
            <ParticlesBackground />
            <div className="login-box">
                <h2>Reset Your Password</h2>
                <p>Please enter and confirm your new password</p>

                <div className="input-group">
                    <FaLock className="icon" />
                    <input
                        type="password"
                        placeholder="New Password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                    />
                </div>

                <div className="input-group">
                    <FaLock className="icon" />
                    <input
                        type="password"
                        placeholder="Confirm New Password"
                        value={confirmPassword}
                        onChange={(e) => setConfirmPassword(e.target.value)}
                    />

                </div>

                <button className="login-button" onClick={handleReset}>Reset Password</button>
                <ToastContainer position="top-right" autoClose={3000} />
            </div>
        </div>
    );
};

export default ResetPasswordPage;
