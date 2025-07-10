import React from 'react';
import './StaffInfoModal.css';

const StaffInfoModal = ({ staff, onClose }) => {
    return (
        <div className="modal-overlay">
            <div className="modal-box">
                <h3>👤 User Info</h3>
                <p><strong>Name:</strong> {staff.name}</p>
                <p><strong>Surname:</strong> {staff.surname}</p>
                <p><strong>TCKN:</strong> {staff.identity}</p>
                <p><strong>Unit:</strong> {staff.unit}</p>
                <p><strong>Position:</strong> {staff.position}</p>
                <button onClick={onClose}>Close</button>
            </div>
        </div>
    );
};

export default StaffInfoModal;
