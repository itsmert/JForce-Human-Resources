import React, { useEffect, useState } from "react";
import axios from "axios";
import "./InventoryAssignModal.css";

const InventoryAssignModal = ({ visible, onClose, selectedStaff, onAssignmentSuccess }) => {
    const [staffList, setStaffList] = useState([]);
    const [inventoryTypes, setInventoryTypes] = useState([]);
    const [showNewTypeInput, setShowNewTypeInput] = useState(false);
    const [newTypeName, setNewTypeName] = useState("");
    const [form, setForm] = useState({
        staffId: selectedStaff?.id || "",
        assignDate: new Date().toISOString().slice(0, 10),
        typeId: "",
        brand: "",
        model: "",
        serialNumber: "",
        entryDate: "",
        status: "On_Staff"
    });
    const [chosenStaff, setChosenStaff] = useState(selectedStaff || null);

    useEffect(() => {
        if (visible) {
            fetchStaff();
            fetchInventoryTypes();
            setChosenStaff(selectedStaff || null);
            setForm(prev => ({
                ...prev,
                staffId: selectedStaff?.id || "",
                typeId: "",
                brand: "",
                model: "",
                serialNumber: "",
                entryDate: "",
                status: "On_Staff"
            }));
        }
    }, [visible, selectedStaff]);

    const fetchStaff = () => {
        const token = localStorage.getItem("token");
        axios.get("http://localhost:8080/api/staff/all", {
            headers: { Authorization: `Bearer ${token}` },
        })
            .then(res => setStaffList(res.data))
            .catch(() => alert("Personel verileri alınamadı."));
    };

    const fetchInventoryTypes = () => {
        const token = localStorage.getItem("token");
        axios.get("http://localhost:8080/api/inventory-types/available-or-unassigned", {
            headers: { Authorization: `Bearer ${token}` }
        })
            .then(res => setInventoryTypes(res.data))
            .catch(() => alert("Müsait veya atanmamış envanter türleri alınamadı."));
    };

    const handleInputChange = (field) => (event) => {
        const value = event.target.value;
        setForm(prev => ({
            ...prev,
            [field]: value
        }));
    };

    const handleChooseStaff = (staff) => {
        setForm(prev => ({
            ...prev,
            staffId: staff.id
        }));
        setChosenStaff(staff);
    };

    const isFormValid = () => {
        const { staffId, assignDate, typeId, brand, model, serialNumber, entryDate } = form;
        return staffId && assignDate && typeId && brand && model && serialNumber && entryDate;
    };

    const handleSubmit = () => {
        const token = localStorage.getItem("token");

        if (!isFormValid()) {
            alert("Please Fill The All Lines");
            return;
        }

        axios.post("http://localhost:8080/api/staff-inventory/assign-with-inventory", {
            staffId: form.staffId,
            assignDate: form.assignDate,
            inventory: {
                typeId: parseInt(form.typeId),
                brand: form.brand,
                model: form.model,
                serialNumber: form.serialNumber,
                entryDate: form.entryDate,
                status: "On_Staff"
            }
        }, {
            headers: { Authorization: `Bearer ${token}` }
        })
            .then(() => {
                alert("Inventory Added On Staff Successfully!");
                onClose();
                onAssignmentSuccess();
            })
            .catch(() => alert("Zimmet atama başarısız."));
    };

    if (!visible) return null;

    return (
        <div className="modal-overlay">
            <div className="modal-content">
                <h3>Add New Debit</h3>

                <h4>Choose Personal</h4>
                <table className="staff-table">
                    <thead>
                    <tr><th>Name</th><th>Surname</th><th>Username</th><th>Select Staff</th></tr>
                    </thead>
                    <tbody>
                    {staffList.map(staff => (
                        <tr key={staff.id}>
                            <td>{staff.name}</td>
                            <td>{staff.surname}</td>
                            <td>{staff.username}</td>
                            <td>
                                <button onClick={() => handleChooseStaff(staff)}>Choose</button>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>

                <h4>Debt Information</h4>

                {chosenStaff && (
                    <div className="preview-box">
                        <h5>📄 Preview</h5>
                        <p><strong>Name: </strong> {chosenStaff.name}</p>
                        <p><strong>Surname: </strong> {chosenStaff.surname}</p>
                        <p><strong>Username: </strong> {chosenStaff.username}</p>

                        <label>Inventory Type</label>
                        <select
                            value={form.typeId}
                            onChange={(e) => {
                                const val = e.target.value;
                                if (val === "other") {
                                    setShowNewTypeInput(true);
                                    setForm(prev => ({ ...prev, typeId: "" }));
                                } else {
                                    setShowNewTypeInput(false);
                                    setForm(prev => ({ ...prev, typeId: parseInt(val) }));
                                }
                            }}
                        >
                            {inventoryTypes.map((type) => (
                                <option key={type.id} value={type.id}>{type.typeName}</option>
                            ))}
                            <option value="other">+ Add New Type</option>
                        </select>

                        {showNewTypeInput && (
                            <div style={{ marginTop: "10px" }}>
                                <input
                                    type="text"
                                    placeholder="Enter new inventory type"
                                    value={newTypeName}
                                    onChange={(e) => setNewTypeName(e.target.value)}
                                />
                                <button
                                    onClick={async () => {
                                        const token = localStorage.getItem("token");
                                        try {
                                            const response = await axios.post(
                                                "http://localhost:8080/api/inventory-types",
                                                { typeName: newTypeName },
                                                { headers: { Authorization: `Bearer ${token}` } }
                                            );
                                            const newType = response.data;
                                            const updatedTypes = [...inventoryTypes, newType];
                                            setInventoryTypes(updatedTypes);
                                            setForm(prev => ({ ...prev, typeId: newType.id }));
                                            setNewTypeName("");
                                            setShowNewTypeInput(false);
                                        } catch (err) {
                                            alert("Error");
                                        }
                                    }}
                                >
                                    Add
                                </button>
                            </div>
                        )}

                        <label>Entry Date</label>
                        <input type="date" value={form.entryDate} onChange={handleInputChange("entryDate")} />

                        <label>Brand</label>
                        <input type="text" value={form.brand} onChange={handleInputChange("brand")} />

                        <label>Model</label>
                        <input type="text" value={form.model} onChange={handleInputChange("model")} />

                        <label>Serial Number</label>
                        <input type="text" value={form.serialNumber} onChange={handleInputChange("serialNumber")} />

                        <label>Assign Date</label>
                        <input type="date" value={form.assignDate} onChange={handleInputChange("assignDate")} />
                    </div>
                )}

                <div className="modal-buttons">
                    <button onClick={onClose}>Cancel</button>
                    <button onClick={handleSubmit} disabled={!isFormValid()}>Assign</button>
                </div>
            </div>
        </div>
    );
};

export default InventoryAssignModal;