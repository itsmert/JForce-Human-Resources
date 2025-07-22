import React from 'react';
import axios from 'axios';
import { saveAs } from 'file-saver';
import { useNavigate } from 'react-router-dom';
import ParticlesBackground from "../Components/ParticlesBackground";
import './ReportsPageForHumanResources.css'; // CSS stilini dışa alalım

const ReportsPage = () => {
    const navigate = useNavigate();

    const downloadCSV = async () => {
        try {
            const token = localStorage.getItem("token");
            const response = await axios.get("http://localhost:8080/api/staff/export/csv", {
                headers: {
                    Authorization: `Bearer ${token}`
                },
                responseType: 'blob'
            });

            const blob = new Blob([response.data], { type: "text/csv;charset=utf-8;" });
            saveAs(blob, "staff_report.csv");
        } catch (error) {
            console.error("Error", error);
            alert("Error");
        }
    };

    return (
        <div className="reports-page-wrapper">
            <ParticlesBackground />
            <div className="reports-container">
                <h2>📋 Staff Report</h2>
                <button className="back-button" onClick={() => navigate(-1)}>← Back</button>
                <button className="download-button" onClick={downloadCSV}>⬇ Download CSV</button>
            </div>
        </div>
    );
};

export default ReportsPage;
