import React from "react";
import {
    BarChart, Bar, XAxis, YAxis, Tooltip, ResponsiveContainer,
    PieChart, Pie, Cell, Legend
} from "recharts";
import "./HRChartsPanel.css";

const HRChartsPanel = () => {
    const departmentData = [
        { name: "IT", value: 1 },
        { name: "HR", value: 2 },
    ];

    const positionData = [
        { name: "Backend Developer", value: 1 },
        { name: "HR Manager", value: 1 },
    ];

    const COLORS = ["#8cd9b3", "#b39ddb"];

    return (
        <div className="chart-container">
            <div className="chart-card">
                <h3 className="chart-title">Staff by Department</h3>
                <ResponsiveContainer width="100%" height={300}>
                    <BarChart data={departmentData}>
                        <XAxis dataKey="name" stroke="#fff" />
                        <YAxis
                            stroke="#fff"
                            tickFormatter={(tick) => Number.isInteger(tick) ? tick : ''}
                        />
                        <Tooltip />
                        <Bar dataKey="value" fill="#8cd9b3" />
                    </BarChart>
                </ResponsiveContainer>
            </div>

            <div className="chart-card">
                <h3 className="chart-title">Staff by Position</h3>
                <ResponsiveContainer width="100%" height={300}>
                    <PieChart>
                        <Pie
                            data={positionData}
                            cx="50%"
                            cy="50%"
                            label
                            outerRadius={90}
                            dataKey="value"
                        >
                            {positionData.map((entry, index) => (
                                <Cell key={`cell-${index}`} fill={COLORS[index % COLORS.length]} />
                            ))}
                        </Pie>
                        <Legend />
                    </PieChart>
                </ResponsiveContainer>
            </div>
        </div>
    );
};

export default HRChartsPanel;
