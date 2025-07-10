import React, { useState } from 'react';
import './HumanResourcesUserMenu.css';

const HumanResourcesUserMenu = ({ username, onLogout, onViewProfile }) => {
    const [showMenu, setShowMenu] = useState(false);

    const toggleMenu = () => {
        setShowMenu(!showMenu);
    };

    const handleLogout = () => {
        setShowMenu(false);
        onLogout();
    };

    const handleViewProfile = () => {
        setShowMenu(false);
        onViewProfile();
    };

    return (
        <div className="user-menu-wrapper">
            <div className="username" onClick={toggleMenu}>
                👤 {username}
            </div>
            {showMenu && (
                <div className="dropdown-menu">
                    <div onClick={handleViewProfile}>🔍 View Profile</div>
                    <div onClick={handleLogout}>🚪 Logout</div>
                </div>
            )}
        </div>
    );
};

export default HumanResourcesUserMenu;
