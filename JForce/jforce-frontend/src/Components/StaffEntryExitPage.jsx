import React, { useEffect, useState } from "react";
import axios from "axios";
import "./StaffEntryExit.css";
import ParticlesBackground from "../Components/ParticlesBackground";
import { saveAs } from "file-saver";

const StaffEntryExitManagementPage = () => {
    const [staffList, setStaffList] = useState([]);
    const [selectedStaff, setSelectedStaff] = useState(null);
    const [entryExitData, setEntryExitData] = useState({
        entryDate: "",
        position: "",
        title: "",
        exitDate: "",
        exitReason: ""
    });
    const [entryHistory, setEntryHistory] = useState([]);
    const [allEntryExit, setAllEntryExit] = useState([]);

    // CSV indirme fonksiyonu
    const downloadCSV = async () => {
        const token = localStorage.getItem("token");
        try {
            const response = await axios.get("http://localhost:8080/api/staff-entry-exit/export/csv", {
                headers: { Authorization: `Bearer ${token}` },
                responseType: "blob"
            });
            const blob = new Blob([response.data], { type: "text/csv;charset=utf-8;" });
            saveAs(blob, "entry_exit_report.csv");
        } catch (error) {
            alert("CSV download failed.");
            console.error(error);
        }
    };

    useEffect(() => {
        const token = localStorage.getItem("token");
        axios.get("http://localhost:8080/api/staff-entry-exit/dto/all", {
            headers: { Authorization: `Bearer ${token}` }
        }).then(res => setAllEntryExit(res.data));
    }, []);

    useEffect(() => {
        const token = localStorage.getItem("token");
        axios.get("http://localhost:8080/api/staff/all", {
            headers: { Authorization: `Bearer ${token}` }
        }).then(res => setStaffList(res.data));
    }, []);

    useEffect(() => {
        if (selectedStaff) {
            const token = localStorage.getItem("token");
            axios.get(`http://localhost:8080/api/staff-entry-exit/${selectedStaff.id}`, {
                headers: { Authorization: `Bearer ${token}` }
            })
                .then(res => setEntryExitData(res.data))
                .catch(() => setEntryExitData({
                    entryDate: "",
                    position: "",
                    title: "",
                    exitDate: "",
                    exitReason: ""
                }));

            axios.get("http://localhost:8080/api/staff-entry-exit/all", {
                headers: { Authorization: `Bearer ${token}` }
            }).then(res => {
                const filtered = res.data.filter(item => item.username === selectedStaff.username);
                setEntryHistory(filtered);
            });
        }
    }, [selectedStaff]);

    const handleChange = (e) => {
        setEntryExitData({ ...entryExitData, [e.target.name]: e.target.value });
    };

    const handleSubmit = () => {
        const token = localStorage.getItem("token");
        axios.post(`http://localhost:8080/api/staff-entry-exit/${selectedStaff.id}`, entryExitData, {
            headers: { Authorization: `Bearer ${token}` }
        })
            .then(() => {
                alert("Record Added!");
                setEntryHistory(prev => [...prev, {
                    ...entryExitData,
                    name: selectedStaff.name,
                    surname: selectedStaff.surname,
                    username: selectedStaff.username
                }]);
            })
            .catch(() => alert("Failed to add."));
    };

    return (
        <>
            <ParticlesBackground />
            <div className="entry-exit-wrapper">
                {/* Staff List */}
                <div className="staff-list">
                    <h3>Staff List</h3>
                    <table>
                        <thead>
                        <tr>
                            <th>Name</th>
                            <th>Surname</th>
                            <th>Username</th>
                            <th>Staff Info</th>
                        </tr>
                        </thead>
                        <tbody>
                        {staffList.map((staff) => (
                            <tr key={staff.id}>
                                <td>{staff.name}</td>
                                <td>{staff.surname}</td>
                                <td>{staff.username}</td>
                                <td>
                                    <button onClick={() => setSelectedStaff(staff)}>Choose</button>
                                </td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                </div>

                {/* Entry/Exit Form */}
                <div className="entry-form">
                    <h3>
                        Entry/Exit Info {selectedStaff && `for: ${selectedStaff.name} ${selectedStaff.surname}`}
                    </h3>
                    {selectedStaff && (
                        <>
                            <label>Entry Date</label>
                            <input type="date" name="entryDate" value={entryExitData.entryDate || ""} onChange={handleChange} />

                            <label>Position</label>
                            <input type="text" name="position" value={entryExitData.position || ""} onChange={handleChange} />

                            <label>Title</label>
                            <input type="text" name="title" value={entryExitData.title || ""} onChange={handleChange} />

                            <label>Exit Date</label>
                            <input type="date" name="exitDate" value={entryExitData.exitDate || ""} onChange={handleChange} />

                            <label>Exit Reason</label>
                            <textarea name="exitReason" value={entryExitData.exitReason || ""} onChange={handleChange} />

                            <button onClick={handleSubmit}>Save</button>

                            {/* History */}
                            <div className="entry-history-table">
                                <h4>Entry-Exit History</h4>
                                <table>
                                    <thead>
                                    <tr>
                                        <th>Entry Date</th>
                                        <th>Position</th>
                                        <th>Title</th>
                                        <th>Exit Date</th>
                                        <th>Reason</th>
                                        <th>Edit</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    {entryHistory.length > 0 ? entryHistory.map((item, index) => (
                                        <tr key={index}>
                                            <td>{item.entryDate || "-"}</td>
                                            <td>{item.position || "-"}</td>
                                            <td>{item.title || "-"}</td>
                                            <td>{item.exitDate || "-"}</td>
                                            <td>{item.exitReason || "-"}</td>
                                            <td><button onClick={() => setEntryExitData(item)}>✎</button></td>
                                        </tr>
                                    )) : (
                                        <tr><td colSpan="6">No Record Found.</td></tr>
                                    )}
                                    </tbody>
                                </table>
                            </div>
                        </>
                    )}
                </div>

                {/* All Staff Entry/Exit Records */}
                <div className="all-entry-exit-table">
                    <div className="header-with-button">
                        <h4>All Staff Information With Entry-Exit Status</h4>
                        <button className="download-csv-button" onClick={downloadCSV}>Download CSV</button>
                    </div>
                    <table>
                        <thead>
                        <tr>
                            <th>Name</th>
                            <th>Surname</th>
                            <th>Username</th>
                            <th>Entry</th>
                            <th>Position</th>
                            <th>Title</th>
                            <th>Exit</th>
                            <th>Reason</th>
                        </tr>
                        </thead>
                        <tbody>
                        {allEntryExit.length > 0 ? allEntryExit.map((item, index) => (
                            <tr key={index}>
                                <td>{item.name}</td>
                                <td>{item.surname}</td>
                                <td>{item.username}</td>
                                <td>{item.entryDate}</td>
                                <td>{item.position}</td>
                                <td>{item.title}</td>
                                <td>{item.exitDate}</td>
                                <td>{item.exitReason}</td>
                            </tr>
                        )) : (
                            <tr><td colSpan="8">No Records Founded</td></tr>
                        )}
                        </tbody>
                    </table>
                </div>
            </div>
        </>
    );
};

export default StaffEntryExitManagementPage;
