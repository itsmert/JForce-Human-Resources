import React, { useState, useEffect } from 'react';
import ParticlesBackground from '../Components/ParticlesBackground';
import HumanResourcesSideBar from '../Components/HumanResourcesSideBar';
import HumanResourcesStatsPanel from '../Components/HumanResourcesStatsPanel';
import HumanResourcesUserMenu from '../Components/HumanResourcesUserMenu';
import StaffInfoModal from '../Components/StaffInfoModal';
import HRChartsPanel from '../Components/HRChartsPanel'; //
import PaginatedStaffTable from '../Components/PaginatedStaffTable';
import './HumanResourcesDashboard.css';
import 'react-toastify/dist/ReactToastify.css';

const HumanResourcesDashboard = () => {
    const [filters, setFilters] = useState({
        name: '',
        surname: '',
        identity: '',
        unit: '',
    });

    const [showUserInfo, setShowUserInfo] = useState(false);
    const [username, setUsername] = useState('');

    const [staffList] = useState([
        { id: 1, name: 'Ali', surname: 'Yılmaz', identity: '12345678901', unit: 'IT', position: 'Backend Developer' },
        { id: 2, name: 'Ayşe', surname: 'Demir', identity: '98765432100', unit: 'HR', position: 'HR Manager' },
        // Buraya daha fazla örnek veri eklersen pagination görünür olacak
    ]);

    useEffect(() => {
        const loggedUser = localStorage.getItem("username");
        setUsername(loggedUser || "Unknown User");
    }, []);

    const handleFilterChange = (e) => {
        setFilters({ ...filters, [e.target.name]: e.target.value });
    };

    const handleUpdate = (id) => {
        alert(`Redirect to update form for ID: ${id}`);
    };

    const handleNewStaff = () => {
        alert('Redirect to New Staff form');
    };

    const handleLogout = () => {
        alert('Logging out...');
    };

    const userStaffInfo = staffList.find(s => s.name.toLowerCase() === username?.split('@')[0]?.toLowerCase());

    const filteredList = staffList.filter((staff) =>
        staff.name.toLowerCase().includes(filters.name.toLowerCase()) &&
        staff.surname.toLowerCase().includes(filters.surname.toLowerCase()) &&
        staff.identity.includes(filters.identity) &&
        staff.unit.toLowerCase().includes(filters.unit.toLowerCase())
    );

    return (
        <div className="dashboard-wrapper">
            <ParticlesBackground />

            <div className="layout-container">
                <HumanResourcesSideBar onNewStaff={handleNewStaff} />

                <div className="main-content-area">
                    <div className="top-bar">
                        <HumanResourcesUserMenu
                            username={username}
                            onLogout={handleLogout}
                            onViewProfile={() => setShowUserInfo(true)}
                        />
                    </div>

                    {/* İstatistiksel panel (grafikler) */}
                    <HRChartsPanel staffList={staffList} />
                    <PaginatedStaffTable staffList={filteredList} />

                    {/* Dashboard başlık ve personel listesi */}
                    <div className="dashboard-box">
                        <h2 className="dashboard-title"></h2>

                        {/* Filtreler (isteğe bağlı olarak ekleyebiliriz) */}
                        {/* <StaffFilter filters={filters} onChange={handleFilterChange} /> */}

                        {/* Sayfalı personel tablosu */}
                        <PaginatedStaffTable staffList={filteredList} onUpdate={handleUpdate} />
                    </div>
                </div>
            </div>

            {showUserInfo && userStaffInfo && (
                <StaffInfoModal staff={userStaffInfo} onClose={() => setShowUserInfo(false)} />
            )}
        </div>
    );
};

export default HumanResourcesDashboard;
