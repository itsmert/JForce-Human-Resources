import React, { useState } from 'react';
import './HumanResourcesUserMenu.css';
import { useNavigate } from 'react-router-dom';

const HumanResourcesUserMenu = ({ username, onViewProfile }) => {
    const [showMenu, setShowMenu] = useState(false);
    const navigate = useNavigate();

    const toggleMenu = () => {
        setShowMenu(!showMenu);
    };

    const handleLogout = () => {
        localStorage.removeItem("token");
        navigate("/..");
    };

    return (
        <div className="user-menu-wrapper">
            <div className="username" onClick={toggleMenu}>
                👤 {username}
            </div>
            {showMenu && (
                <div className="dropdown-menu">
                    <div onClick={handleLogout}>🚪 Logout</div>
                </div>
            )}
        </div>
    );
};

export default HumanResourcesUserMenu;
