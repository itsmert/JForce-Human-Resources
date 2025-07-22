import React, { useState, useEffect } from 'react';
import './NewStaffPage.css';
import axios from 'axios';
import ParticlesBackground from '../Components/ParticlesBackground';
import { useNavigate } from 'react-router-dom';

const NewStaffPage = () => {
    const navigate = useNavigate();

    const [formData, setFormData] = useState({
        name: '',
        surname: '',
        username: '',
        mail: '',
        turkishIdentity: '',
        password: '',
        role: 'Human_Resources',
        registrationNumber: '',
        unit: null,
        work: null,
        workingStatus: true,
        maritalStatus: '',
        sex: '',
        graduationStatus: '',
        dateOfBirth: ''
    });

    const [unitOptions, setUnitOptions] = useState([]);
    const [workOptions, setWorkOptions] = useState([]);

    useEffect(() => {
        const token = localStorage.getItem("token");

        axios.get("http://localhost:8080/api/unit", {
            headers: { Authorization: `Bearer ${token}` }
        })
            .then(res => setUnitOptions(res.data))
            .catch(err => console.error("Unit fetch failed:", err));

        axios.get("http://localhost:8080/api/work", {
            headers: { Authorization: `Bearer ${token}` }
        })
            .then(res => setWorkOptions(res.data))
            .catch(err => console.error("Work fetch failed:", err));
    }, []);

    const handleChange = (e) => {
        const { name, value, type, checked } = e.target;

        if (name === "unit") {
            const selected = unitOptions.find(u => u.id === parseInt(value));
            setFormData(prev => ({ ...prev, unit: selected || null }));
        } else if (name === "work") {
            const selected = workOptions.find(w => w.id === parseInt(value));
            setFormData(prev => ({ ...prev, work: selected || null }));
        } else {
            setFormData(prev => ({
                ...prev,
                [name]: type === 'checkbox' ? checked : value
            }));
        }
    };

    const handleSubmit = (e) => {
        e.preventDefault();

        const payload = {
            ...formData,
            unit_id: formData.unit?.id,
            work_id: formData.work?.id
        };

        axios.post("http://localhost:8080/api/staff/register", payload, {
            headers: {
                Authorization: `Bearer ${localStorage.getItem("token")}`
            }
        })
            .then(() => {
                alert("Staff member added successfully!");
                setFormData({
                    name: '',
                    surname: '',
                    username: '',
                    mail: '',
                    turkishIdentity: '',
                    password: '',
                    role: 'Human_Resources',
                    registrationNumber: '',
                    unit: null,
                    work: null,
                    workingStatus: true,
                    maritalStatus: '',
                    sex: '',
                    graduationStatus: '',
                    dateOfBirth: ''
                });
            })
            .catch(err => {
                console.error(err);
                alert("Error while adding staff");
            });
    };

    return (
        <div className="new-staff-page-wrapper">
            <ParticlesBackground />

            <div className="new-staff-container">

                <h2>Add New Staff Member</h2>

                <form className="staff-form" onSubmit={handleSubmit}>
                    <input name="name" placeholder="Name" value={formData.name} onChange={handleChange} required />
                    <input name="surname" placeholder="Surname" value={formData.surname} onChange={handleChange} required />
                    <input name="username" placeholder="Username" value={formData.username} onChange={handleChange} required />
                    <input name="mail" type="email" placeholder="Mail" value={formData.mail} onChange={handleChange} required />
                    <input name="turkishIdentity" placeholder="Turkish Identity" value={formData.turkishIdentity} onChange={handleChange} required />

                    <select name="role" value={formData.role} onChange={handleChange} required>
                        <option value="Human_Resources">Human Resources</option>
                        <option value="Inventory_Manager">Inventory Manager</option>
                        <option value="Admin">Admin</option>
                    </select>

                    <input name="registrationNumber" placeholder="Registration Number" value={formData.registrationNumber} onChange={handleChange} required />

                    <select name="unit" value={formData.unit?.id || ""} onChange={handleChange} required>
                        <option value="">Select Unit</option>
                        {unitOptions.map(unit => (
                            <option key={unit.id} value={unit.id}>{unit.name}</option>
                        ))}
                    </select>

                    <select name="work" value={formData.work?.id || ""} onChange={handleChange} required>
                        <option value="">Select Work Type</option>
                        {workOptions.map(work => (
                            <option key={work.id} value={work.id}>{work.name}</option>
                        ))}
                    </select>

                    <input name="dateOfBirth" type="date" value={formData.dateOfBirth} onChange={handleChange} required />

                    <select name="sex" value={formData.sex} onChange={handleChange} required>
                        <option value="">Sex</option>
                        <option value="M">Male</option>
                        <option value="F">Female</option>
                    </select>

                    <select name="maritalStatus" value={formData.maritalStatus} onChange={handleChange} required>
                        <option value="">Marital Status</option>
                        <option value="Single">Single</option>
                        <option value="Married">Married</option>
                    </select>

                    <select name="graduationStatus" value={formData.graduationStatus} onChange={handleChange} required>
                        <option value="">Graduation Status</option>
                        <option value="Graduate">Graduate</option>
                        <option value="Undergraduate">Undergraduate</option>
                        <option value="PhD">PhD</option>
                    </select>

                    <label>
                        Working Status:
                        <input name="workingStatus" type="checkbox" checked={formData.workingStatus} onChange={handleChange} />
                    </label>

                    <button
                        style={{
                            marginBottom: '15px',
                            padding: '8px 12px',
                            backgroundColor: '#444',
                            color: '#fff',
                            border: 'none',
                            borderRadius: '6px',
                            cursor: 'pointer',
                            fontWeight: 'bold'
                        }}
                        onClick={() => navigate(-1)}
                    >
                        Back
                    </button>
                    <button type="submit">Add Staff</button>
                </form>
            </div>
        </div>
    );
};

export default NewStaffPage;
