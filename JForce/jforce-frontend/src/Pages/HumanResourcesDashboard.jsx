import React, { useState, useEffect } from 'react';
import ParticlesBackground from '../Components/ParticlesBackground';
import HumanResourcesSideBar from '../Components/HumanResourcesSideBar';
import HumanResourcesUserMenu from '../Components/HumanResourcesUserMenu';
import StaffInfoModal from '../Components/StaffInfoModal';
import HRChartsPanel from '../Components/HRChartsPanel';
import PaginatedStaffTable from '../Components/PaginatedStaffTable';
import './HumanResourcesDashboard.css';
import 'react-toastify/dist/ReactToastify.css';

const HumanResourcesDashboard = () => {
    const [filters, setFilters] = useState({
        name: '',
        surname: '',
        turkish_identity: '',
        unitName: '',
    });

    const [showUserInfo, setShowUserInfo] = useState(false);
    const [username, setUsername] = useState('');
    const [staffList, setStaffList] = useState([]);
    const [userStaffInfo, setUserStaffInfo] = useState(null);
    const [unitOptions, setUnitOptions] = useState([]);

    useEffect(() => {
        const token = localStorage.getItem("token");
        fetch("http://localhost:8080/api/unit", {
            headers: { Authorization: `Bearer ${token}` }
        })
            .then(res => res.ok ? res.json() : [])
            .then(data => {
                setUnitOptions(data);
            })
            .catch(err => console.error("Error loading units:", err));
    }, []);

    useEffect(() => {
        const token = localStorage.getItem("token");
        fetch("http://localhost:8080/api/staff/all", {
            headers: { Authorization: `Bearer ${token}` }
        })
            .then(res => res.json())
            .then(data => setStaffList(data || []))
            .catch(err => console.error("FETCH Error:", err));
    }, []);

    useEffect(() => {
        const loggedUser = localStorage.getItem("username");
        setUsername(loggedUser || "Unknown User");
    }, []);

    // username ya da staffList değiştiğinde userStaffInfo güncellenir
    useEffect(() => {
        const staff = staffList.find(s => s.username === username);
        if (staff && staff.unit) {
            setUserStaffInfo({
                ...staff,
                unitId: staff.unit.id  // unitId de ekle ki select çalışsın
            });
        } else {
            setUserStaffInfo(staff || null);
        }
    }, [staffList, username]);

    // input değişimlerini state'e kaydet
    const handleChange = (e) => {
        const { name, value, type, checked } = e.target;
        const newValue = type === 'checkbox' ? checked : value;
        setUserStaffInfo(prev => ({
            ...prev,
            [name]: newValue
        }));
    };

    // filtreleme işlemi
    const filteredList = staffList.filter((staff) =>
        (staff.name || '').toLowerCase().includes(filters.name.toLowerCase()) &&
        (staff.surname || '').toLowerCase().includes(filters.surname.toLowerCase()) &&
        (staff.turkishIdentity || '').includes(filters.turkish_identity) &&
        (filters.unitName === '' || (staff.unit?.name || '') === filters.unitName)
    );

    return (
        <div className="dashboard-wrapper">
            <ParticlesBackground />
            <div className="layout-container">
                <HumanResourcesSideBar onNewStaff={() => alert('Redirect to New Staff form')} />
                <div className="main-content-area">
                    <div className="top-bar">
                        <HumanResourcesUserMenu
                            username={username}
                            onLogout={() => alert('Logging out...')}
                            onViewProfile={() => setShowUserInfo(true)}
                        />
                    </div>
                    <HRChartsPanel staffList={staffList} />
                    <PaginatedStaffTable staffList={filteredList} onUpdate={() => alert('Update')} />
                </div>
            </div>

            {showUserInfo && userStaffInfo && unitOptions.length > 0 && (
                <StaffInfoModal
                    staff={userStaffInfo}
                    isEditMode={true}
                    onClose={() => setShowUserInfo(false)}
                    onChange={handleChange} // <-- burada
                    onSave={() => console.log("Save not implemented")}
                />
            )}
        </div>
    );
};

export default HumanResourcesDashboard;
