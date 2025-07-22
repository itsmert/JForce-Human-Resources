import React, { useEffect, useState } from "react";
import axios from "axios";
import "./InventoryAssignmentPage.css";
import ParticlesBackground from "../Components/ParticlesBackground";
import InventoryAssignModal from "../Pages/InventoryAssignModal";

const InventoryAssignmentPage = () => {
    const [assignments, setAssignments] = useState([]);
    const [filter, setFilter] = useState({ name: "", surname: "", registrationNumber: "" });
    const [showModal, setShowModal] = useState(false);
    const [selectedStaff, setSelectedStaff] = useState(null);



    useEffect(() => {
        fetchAssignments();
    }, []);

    const fetchAssignments = () => {
        const token = localStorage.getItem("token");
        axios.get("http://localhost:8080/api/staff-inventory/dto/all", {
            headers: { Authorization: `Bearer ${token}` }
        })
            .then(res => setAssignments(res.data))
            .catch(() => alert("Zimmet listesi alınamadı."));
    };

    const filteredAssignments = assignments.filter(a => {
        const nameMatch = filter.name === "" || a.staffName.toLowerCase().includes(filter.name.toLowerCase());
        const surnameMatch = filter.surname === "" || a.staffSurname.toLowerCase().includes(filter.surname.toLowerCase());
        const regMatch = filter.registrationNumber === "" || a.username === filter.registrationNumber;
        return nameMatch && surnameMatch && regMatch;
    });

    const handleReturn = (assignmentId) => {
        const token = localStorage.getItem("token");
        axios.put(`http://localhost:8080/api/staff-inventory/return`, null, {
            headers: { Authorization: `Bearer ${token}` },
            params: {
                assignmentId: assignmentId,
                returnDate: new Date().toISOString().slice(0, 10) // bugün
            }
        })
            .then(() => {
                alert("Zimmet iadesi başarıyla kaydedildi ✅");
                fetchAssignments(); // tabloyu yenile
            })
            .catch((err) => {
                console.error(err);
                alert("Zimmet iadesi başarısız ❌");
            });
    };


    return (
        <>
            <ParticlesBackground className="particles-bg" />

            <div className="inventory-assignments-wrapper">
                <h2>📦 Debt Controller</h2>

                {/* 🔍 Filtreleme */}
                <div className="filter-bar">
                    <input
                        type="text"
                        placeholder="Filter By Name"
                        value={filter.name}
                        onChange={e => setFilter({ ...filter, name: e.target.value })}
                    />
                    <input
                        type="text"
                        placeholder="Filter By Surname"
                        value={filter.surname}
                        onChange={e => setFilter({ ...filter, surname: e.target.value })}
                    />
                    <input
                        type="text"
                        placeholder="Filter By Identity"
                        value={filter.registrationNumber}
                        onChange={e => setFilter({ ...filter, registrationNumber: e.target.value })}
                    />
                </div>

                {/* 📋 Tablo */}
                <table className="assignment-table">
                    <thead>
                    <tr>
                        <th>Name</th>
                        <th>Surname</th>
                        <th>Username</th>
                        <th>Inventory Type</th>
                        <th>Brand</th>
                        <th>Model</th>
                        <th>Serial Number</th>
                        <th>Assign Date</th>
                        <th>Status</th> {/* ✅ Yeni */}
                        <th>Return Status</th> {/* ✅ Yeni */}
                        <th>Take Back</th>
                    </tr>
                    </thead>
                    <tbody>
                    {filteredAssignments.map(item => (
                        <tr key={item.assignmentId}>
                            <td>{item.staffName}</td>
                            <td>{item.staffSurname}</td>
                            <td>{item.username}</td>
                            <td>{item.inventoryType}</td>
                            <td>{item.brand}</td>
                            <td>{item.model}</td>
                            <td>{item.serialNumber}</td>
                            <td>{item.assignDate}</td>

                            {/* ✅ Inventory Status */}
                            <td>
                    <span
                        className={`status-tag ${item.returnDate ? "available" : "on-staff"}`}
                    >
                        {item.returnDate ? "Available" : "On_Staff"}
                    </span>
                            </td>

                            {/* ✅ Return Status */}
                            <td>
                    <span
                        className={`return-tag ${item.returnDate ? "returned" : "not-returned"}`}
                    >
                        {item.returnDate ? "✅ Returned" : "⛔ Not Returned"}
                    </span>
                            </td>

                            <td>
                                <button
                                    disabled={item.returnDate !== null}
                                    onClick={() => handleReturn(item.assignmentId)}
                                >
                                    {item.returnDate ? "Returned" : "Return Item"}
                                </button>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>

                {/* ➕ Yeni Zimmet Ekle Butonu */}
                <button
                    className="add-button"
                    onClick={() => {
                        setSelectedStaff(null);
                        setShowModal(true);
                    }}
                >
                    ➕ Add New Stuff
                </button>
            </div>

            {showModal && (
                <InventoryAssignModal
                    visible={showModal}
                    onClose={() => setShowModal(false)}
                    selectedStaff={selectedStaff}
                    onAssignmentSuccess={fetchAssignments}
                />
            )}
        </>
    );
};

export default InventoryAssignmentPage;
