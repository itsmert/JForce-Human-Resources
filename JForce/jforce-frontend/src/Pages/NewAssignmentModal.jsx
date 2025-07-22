import React, { useEffect, useState } from "react";
import axios from "axios";
import "./NewAssignmentModal.css";

const NewAssignmentModal = ({ staff, onClose, onSuccess }) => {
    const [availableInventory, setAvailableInventory] = useState([]);
    const [selectedInventoryId, setSelectedInventoryId] = useState("");
    const [assignDate, setAssignDate] = useState("");

    useEffect(() => {
        const token = localStorage.getItem("token");
        axios.get("http://localhost:8080/api/inventory/available", {
            headers: { Authorization: `Bearer ${token}` }
        })
            .then(res => setAvailableInventory(res.data))
            .catch(() => alert("Uygun envanter listesi alınamadı."));
    }, []);

    const handleAssign = () => {
        if (!selectedInventoryId) return alert("Lütfen bir envanter seçin.");

        const token = localStorage.getItem("token");
        const user = JSON.parse(localStorage.getItem("user")); // JWT decode sonucu örneği

        axios.post("http://localhost:8080/api/staff-inventory/assign", null, {
            headers: { Authorization: `Bearer ${token}` },
            params: {
                staffId: staff.id,
                inventoryId: selectedInventoryId,
                assignDate: assignDate || null,
                assignedById: user?.id || null
            }
        })
            .then(() => {
                alert("Zimmet başarıyla eklendi.");
                onSuccess();  // tablonun yeniden yüklenmesi için
                onClose();    // popup kapansın
            })
            .catch(() => alert("Zimmet eklenemedi."));
    };

    return (
        <div className="modal-overlay">
            <div className="modal">
                <h3>➕ Yeni Zimmet Ata ({staff.name} {staff.surname})</h3>

                <label>Envanter Seç:</label>
                <select value={selectedInventoryId} onChange={(e) => setSelectedInventoryId(e.target.value)}>
                    <option value="">-- Envanter Seçin --</option>
                    {availableInventory.map(inv => (
                        <option key={inv.id} value={inv.id}>
                            {inv.type} - {inv.brand} {inv.model} ({inv.serialNumber})
                        </option>
                    ))}
                </select>

                <label>Zimmet Tarihi:</label>
                <input
                    type="date"
                    value={assignDate}
                    onChange={(e) => setAssignDate(e.target.value)}
                />

                <div className="modal-buttons">
                    <button onClick={handleAssign}>Zimmetle</button>
                    <button className="cancel-button" onClick={onClose}>İptal</button>
                </div>
            </div>
        </div>
    );
};

export default NewAssignmentModal;
