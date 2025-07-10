import React, { useState } from 'react';
import './PaginatedStaffTable.css';

const PaginatedStaffTable = ({ staffList }) => {
    const [currentPage, setCurrentPage] = useState(1);
    const recordsPerPage = 10;

    const indexOfLastRecord = currentPage * recordsPerPage;
    const indexOfFirstRecord = indexOfLastRecord - recordsPerPage;
    const currentRecords = staffList.slice(indexOfFirstRecord, indexOfLastRecord);

    const totalPages = Math.ceil(staffList.length / recordsPerPage);

    const handleClick = (page) => setCurrentPage(page);

    return (
        <div className="staff-table-container">
            <h3>📋 Staff List</h3>
            <table className="staff-table">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Surname</th>
                    <th>Identity</th>
                    <th>Unit</th>
                    <th>Position</th>
                </tr>
                </thead>
                <tbody>
                {currentRecords.map((staff) => (
                    <tr key={staff.id}>
                        <td>{staff.id}</td>
                        <td>{staff.name}</td>
                        <td>{staff.surname}</td>
                        <td>{staff.identity}</td>
                        <td>{staff.unit}</td>
                        <td>{staff.position}</td>
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
        </div>
    );
};

export default PaginatedStaffTable;
