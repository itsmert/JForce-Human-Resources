import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import LoginPage from './Pages/LoginPage';
import './Pages/LoginPage.css';
import ResetPasswordPage from './Pages/ResetPasswordPage';
import HumanResourcesDashboard from './Pages/HumanResourcesDashboard';
import NewStaffPage from './Pages/NewStaffPage';
import ReportPageForHumanResources from "./Pages/ReportPageForHumanResources";
import StaffEntryExitPage from "./Components/StaffEntryExitPage";
import InventoryAssignmentPage from "./Pages/InventoryAssignmentPage";
import InventoryAssignModal from "./Pages/InventoryAssignModal";
function App() {
  return (
      <Router>
        <Routes>
          <Route path="/" element={<LoginPage />} />
            <Route path="/reset-password" element={<ResetPasswordPage />} />
            <Route path="/HumanResources-Dashboard" element={<HumanResourcesDashboard />} />
            <Route path="/new-staff" element={<NewStaffPage />} />
            <Route path="/reports" element={<ReportPageForHumanResources />} />
            <Route path="/staff-entry-exit" element={<StaffEntryExitPage />} />
            <Route path="/inventory-assignment" element={<InventoryAssignmentPage />} />
            <Route path="/inventory-assignment-staff" element={<InventoryAssignModal />} />

            {/* <Route path="/dashboard" element={<Dashboard />} /> */}
        </Routes>
      </Router>
  );
}

export default App;
