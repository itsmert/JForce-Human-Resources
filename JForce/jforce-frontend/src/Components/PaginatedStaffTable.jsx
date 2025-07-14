import React, { useState, useEffect } from 'react';
import './PaginatedStaffTable.css';
import StaffInfoModal from './StaffInfoModal';
import axios from "axios";

const PaginatedStaffTable = ({ staffList, onUpdate }) => {
    const [currentPage, setCurrentPage] = useState(1);
    const [filteredList, setFilteredList] = useState([]);
    const [filters, setFilters] = useState({
        name: '',
        surname: '',
        turkishIdentity: '',
        unit_name: '',
    });

    const [selectedStaff, setSelectedStaff] = useState(null);
    const [isEditMode, setIsEditMode] = useState(false);
    const recordsPerPage = 10;

    useEffect(() => {
        const list = staffList.filter((staff) =>
            (staff.name || '').toLowerCase().includes(filters.name.toLowerCase()) &&
            (staff.surname || '').toLowerCase().includes(filters.surname.toLowerCase()) &&
            (staff.turkishIdentity || '').includes(filters.turkishIdentity) &&
            (filters.unit_name === '' || (staff.unit?.name || '') === filters.unit_name)
        );
        setFilteredList(list);
        setCurrentPage(1);
    }, [filters, staffList]);

    const indexOfLastRecord = currentPage * recordsPerPage;
    const indexOfFirstRecord = indexOfLastRecord - recordsPerPage;
    const currentRecords = filteredList.slice(indexOfFirstRecord, indexOfLastRecord);
    const totalPages = Math.ceil(filteredList.length / recordsPerPage);

    const handleClick = (page) => setCurrentPage(page);
    const handleFilterChange = (e) => setFilters({ ...filters, [e.target.name]: e.target.value });

    const handleView = (staff) => {
        setSelectedStaff(staff);
        setIsEditMode(false);
    };

    const [unitOptions, setUnitOptions] = useState([]);

    useEffect(() => {
        const token = localStorage.getItem("token");
        fetch("http://localhost:8080/api/unit", {
            headers: { Authorization: `Bearer ${token}` }
        })
            .then(res => res.ok ? res.json() : [])
            .then(data => setUnitOptions(data))
            .catch(err => console.error("Unit fetch failed:", err));
        console.log("Token:", token);

    }, []);

    const [workOptions, setWorkOptions] = useState([]);

    useEffect(() => {
        const token = localStorage.getItem("token");

        fetch('http://localhost:8080/api/work', {
            headers: {
                Authorization: `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        })
            .then((res) => {
                if (!res.ok) {
                    throw new Error("Failed to fetch work options");
                }
                return res.text();
            })
            .then((text) => {
                if (!text) {
                    setWorkOptions([]);
                    return;
                }
                setWorkOptions(JSON.parse(text));
            })
            .catch((err) => console.error("Work fetch failed:", err));
    }, []);
    const handleCloseModal = () => setSelectedStaff(null);


    const handleSave = async (updatedStaff) => {
        try {
            // DTO oluştur
            const dto = {
                id: updatedStaff.id,
                username: updatedStaff.username,
                name: updatedStaff.name,
                surname: updatedStaff.surname,
                mail: updatedStaff.mail,
                sex: updatedStaff.sex,
                dateOfBirth: updatedStaff.dateOfBirth,
                maritalStatus: updatedStaff.maritalStatus,
                turkishIdentity: updatedStaff.turkishIdentity,
                registrationNumber: updatedStaff.registrationNumber,
                graduationStatus: updatedStaff.graduationStatus,
                workingStatus: updatedStaff.workingStatus,
                password: updatedStaff.password,
                role: updatedStaff.role,
                unit_id: updatedStaff.unit?.id ? Number(updatedStaff.unit.id) : null,
                work_id: updatedStaff.work?.id ? Number(updatedStaff.work.id) : null
            };

            console.log("🚀 Giden DTO:", dto);

            const token = localStorage.getItem("token");
            const response = await axios.put(
                `http://localhost:8080/api/staff/${dto.id}`,
                dto,
                {
                    headers: {
                        "Authorization": `Bearer ${token}`,
                    }
                }
            );

            console.log("✅ Update success:", response.data);
            alert("Personel bilgileri başarıyla güncellendi!");
            handleCloseModal();

            // Sayfayı yenilemek yerine verileri güncelle
            onUpdate && onUpdate();
        } catch (error) {
            console.error("❌ Update failed", error);

            if (error.response) {
                console.error("Response data:", error.response.data);
                console.error("Response status:", error.response.status);
                console.error("Response headers:", error.response.headers);

                if (error.response.status === 403) {
                    alert("Bu işlem için yetkiniz yok! Lütfen yöneticinizle görüşün.");
                } else if (error.response.status === 400) {
                    alert(`Geçersiz istek: ${error.response.data}`);
                } else {
                    alert(`Hata: ${error.response.data?.message || error.message}`);
                }
            } else if (error.request) {
                console.error("Request:", error.request);
                alert("Sunucuya ulaşılamadı. Lütfen bağlantınızı kontrol edin.");
            } else {
                console.error("Error message:", error.message);
                alert(`Hata: ${error.message}`);
            }
        }
    };


    const handleInputChange = (e) => {
        const { name, value } = e.target;

        setSelectedStaff(prev => ({
            ...prev,
            [name]: typeof value === 'object' ? value : value
        }));

        console.log("🧠 onChange →", name, "=", value);
    };

    const handleEdit = (staff, e) => {
        e.stopPropagation();
        const enrichedStaff = {
            ...staff,
            unitId: staff.unit?.id || staff.unitId || null,
            workId: staff.work?.id || '',
            role: staff.role?.toUpperCase().replace(/\s/g, "_") // "Human Resources" → "HUMAN_RESOURCES"
        };
        setSelectedStaff(enrichedStaff);
        setIsEditMode(true);
    };
    const uniqueUnits = [...new Set(staffList.map(staff => staff.unit?.name).filter(Boolean))];

    return (
        <div className="staff-table-container">
            <h3 className="staff-title">Staff List</h3>

            <div className="filter-section">
                <input type="text" name="name" placeholder="Filter by Name" value={filters.name} onChange={handleFilterChange} />
                <input type="text" name="surname" placeholder="Filter by Surname" value={filters.surname} onChange={handleFilterChange} />
                <input type="text" name="turkishIdentity" placeholder="Filter by Turkish Identity" value={filters.turkishIdentity} onChange={handleFilterChange} />
                <select name="unit_name" value={filters.unit_name} onChange={handleFilterChange}>
                    <option value="">All Units</option>
                    {uniqueUnits.map((unit, index) => (
                        <option key={index} value={unit}>{unit}</option>
                    ))}
                </select>
            </div>

            <table className="staff-table">
                <thead>
                <tr>
                    <th></th>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Surname</th>
                    <th>Username</th>
                    <th>Mail</th>
                    <th>Turkish Identity</th>
                    <th>Role</th>
                    <th>Working Status</th>
                    <th>Unit Type</th>
                    <th>Work Type</th>
                </tr>
                </thead>
                <tbody>
                {currentRecords.map((staff) => (
                    <tr key={staff.id} className="clickable-row" onClick={() => handleView(staff)}>
                        <td>
                            <button className="edit-button" onClick={(e) => handleEdit(staff, e)}>✎</button>
                        </td>
                        <td>{staff.id}</td>
                        <td>{staff.name}</td>
                        <td>{staff.surname}</td>
                        <td>{staff.username}</td>
                        <td>{staff.mail}</td>
                        <td>{staff.turkishIdentity}</td>
                        <td>{staff.role}</td>
                        <td>{staff.workingStatus ? '✅' : '❌'}</td>
                        <td>{staff.unit?.name}</td>
                        <td>{staff.work?.name}</td>
                    </tr>
                ))}
                </tbody>
            </table>

            <div className="pagination">
                {Array.from({ length: totalPages }, (_, index) => (
                    <button
                        key={index + 1}
                        className={currentPage === index + 1 ? 'active' : ''}
                        onClick={() => handleClick(index + 1)}
                    >
                        {index + 1}
                    </button>
                ))}
            </div>
            {selectedStaff && (
                <StaffInfoModal
                    staff={selectedStaff}
                    isEditMode={isEditMode}
                    onClose={handleCloseModal}
                    onSave={() => handleSave(selectedStaff)}
                    onChange={handleInputChange}
                    unitOptions={unitOptions}
                    workOptions={workOptions}
                />
            )}
        </div>
    );
};

export default PaginatedStaffTable;
