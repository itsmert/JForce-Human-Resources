import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import LoginPage from './Pages/LoginPage';
import './Pages/LoginPage.css';
import ResetPasswordPage from './Pages/ResetPasswordPage';

function App() {
  return (
      <Router>
        <Routes>
          <Route path="/" element={<LoginPage />} />
            <Route path="/reset-password" element={<ResetPasswordPage />} />

            {/* <Route path="/dashboard" element={<Dashboard />} /> */}
        </Routes>
      </Router>
  );
}

export default App;
