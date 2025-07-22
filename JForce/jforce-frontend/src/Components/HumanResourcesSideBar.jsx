import React from 'react';
import './HumanResourcesSideBar.css';
import { Link } from 'react-router-dom';

const HumanResourcesSideBar = ({ onNewStaff }) => {
    return (
        <div className="sidebar">
            <div className="sidebar-logo">
                <img src="/logo.png" alt="Company Logo" className="logo-img" />
                <div className="sidebar-title">Human Resources Panel</div>

            </div>
            <h3>📁 Menu</h3>
            <ul>
                <li>
                    <Link to="/new-staff" className="sidebar-link">➕ New Staff</Link>
                </li>

                <li>
                    <Link to="/reports" className="sidebar-link">📊 Reports</Link>
                </li>
                <li>
                    <Link to="/staff-entry-exit" className="sidebar-link">➡ Staff Entry Exit Status</Link>
                </li>
                <li>
                    <Link to="/inventory-assignment" className="sidebar-link">📦 Inventory Assignments</Link>
                </li>

            </ul>
        </div>
    );
};

export default HumanResourcesSideBar;
