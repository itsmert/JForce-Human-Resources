import React from 'react';
import './StaffInfoModal.css';

const roleOptions = ["Admin", "Human_Resources", "Inventory_Manager"];
const maritalStatusOptions = ["Single", "Married", "Divorced", "Widowed"];
const graduationStatusOptions = ["Graduated", "UnderGraduated", "PhD"];
const workingStatusOptions = [
    { label: "Active", value: true },
    { label: "Passive", value: false }
];

const StaffInfoModal = ({
                            staff,
                            isEditMode,
                            onClose,
                            onSave,
                            onChange,
                            unitOptions,
                            workOptions
                        }) => {
    if (!staff) return null;

    const handleSelectChange = (e, name, options) => {
        const selectedValue = e.target.value;
        if (name === "unit") {
            const selected = unitOptions.find(u => u.id === parseInt(selectedValue));
            onChange({ target: { name, value: selected || null } });
        } else if (name === "work") {
            const selected = workOptions.find(w => w.id === parseInt(selectedValue));
            onChange({ target: { name, value: selected || null } });
        } else if (name === "workingStatus") {
            onChange({ target: { name, value: selectedValue === "true" } });
        } else {
            onChange({ target: { name, value: selectedValue } });
        }
    };

    return (
        <div className="modal-backdrop">
            <div className="modal-content">
                    <h2>{isEditMode ? "Staff Information Edit" : "Staff Information"}</h2>

                <div className="form-grid">
                    <div className="form-column">
                        <label>Name</label>
                        <input name="name" value={staff.name || ""} onChange={onChange} disabled />

                        <label>Surname</label>
                        <input name="surname" value={staff.surname || ""} onChange={onChange} disabled />

                        <label>Username</label>
                        <input name="username" value={staff.username || ""} onChange={onChange} disabled />

                        <label>Mail</label>
                        <input name="mail" value={staff.mail || ""} onChange={onChange} disabled />

                        <label>Turkish Identity</label>
                        <input name="turkishIdentity" value={staff.turkishIdentity || ""} onChange={onChange} disabled />

                        <label>Registration Number</label>
                        <input name="registrationNumber" value={staff.registrationNumber || ""} onChange={onChange} disabled />

                        <label>Date Of Birth</label>
                        <input type="date" name="dateOfBirth" value={staff.dateOfBirth || ""} onChange={onChange} disabled={!isEditMode} />

                        <label>Password</label>
                        <input name="password" type="password" value={staff.password || ""} onChange={onChange} disabled />
                    </div>

                    <div className="form-column">
                        <label>Role</label>
                        <select
                            name="role"
                            value={staff.role || ""}
                            onChange={e => handleSelectChange(e, "role")}
                            disabled
                        >
                            {roleOptions.map(role => (
                                <option key={role} value={role}>{role}</option>
                            ))}
                        </select>

                        <label>Unit</label>
                        <select name="unit" value={staff.unit?.id || ""} onChange={e => handleSelectChange(e, "unit")} disabled={!isEditMode}>
                            {unitOptions.map(unit => (
                                <option key={unit.id} value={unit.id}>{unit.name}</option>
                            ))}
                        </select>

                        <label>Work</label>
                        <select name="work" value={staff.work?.id || ""} onChange={e => handleSelectChange(e, "work")} disabled={!isEditMode}>
                            {workOptions.map(work => (
                                <option key={work.id} value={work.id}>{work.name}</option>
                            ))}
                        </select>

                        <label>Working Status</label>
                        <select name="workingStatus" value={String(staff.workingStatus)} onChange={e => handleSelectChange(e, "workingStatus")} disabled={!isEditMode}>
                            {workingStatusOptions.map(opt => (
                                <option key={opt.label} value={opt.value}>{opt.label}</option>
                            ))}
                        </select>

                        <label>Marital Status</label>
                        <select name="maritalStatus" value={staff.maritalStatus || ""} onChange={onChange} disabled={!isEditMode}>
                            {maritalStatusOptions.map(status => (
                                <option key={status} value={status}>{status}</option>
                            ))}
                        </select>

                        <label>Graduation Status</label>
                        <select name="graduationStatus" value={staff.graduationStatus || ""} onChange={onChange} disabled={!isEditMode}>
                            {graduationStatusOptions.map(status => (
                                <option key={status} value={status}>{status}</option>
                            ))}
                        </select>

                        <label>Sex</label>
                        <input name="sex" value={staff.sex || ""} onChange={onChange} disabled />
                    </div>
                </div>

                <div className="modal-buttons">
                    <button className="cancel-btn" onClick={onClose}>Cancel</button>
                    {isEditMode && <button className="save-btn" onClick={() => onSave(staff)}>Save</button>}
                </div>
            </div>
        </div>
    );
};

export default StaffInfoModal;
