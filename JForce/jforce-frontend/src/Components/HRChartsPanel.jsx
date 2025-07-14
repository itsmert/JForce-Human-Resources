import React from "react";
import {
    BarChart, Bar, XAxis, YAxis, Tooltip, ResponsiveContainer,
    PieChart, Pie, Cell, Legend
} from "recharts";
import "./HRChartsPanel.css";

/**
 * Component to render department and position statistics using charts.
 */
const HRChartsPanel = ({ staffList }) => {
    // Department (unit) data
    const departmentCounts = staffList.reduce((acc, staff) => {
        const unitName = staff.unit?.name || "Unknown";
        acc[unitName] = (acc[unitName] || 0) + 1;
        return acc;
    }, {});
    const departmentData = Object.entries(departmentCounts).map(([name, value]) => ({ name, value }));

    // Unique colors for each bar in BarChart
    const barColors = ["#8cd9b3", "#b39ddb", "#ffbb28", "#ff8042", "#ffa07a", "#a6d854", "#ffd92f"];

    // Role mapping
    const roleLabelMap = {
        Admin: "Admin",
        Human_Resources: "HR",
        Inventory_Manager: "Inventory"
    };

    const positionCounts = staffList.reduce((acc, staff) => {
        const role = staff.role || "Unknown";
        acc[role] = (acc[role] || 0) + 1;
        return acc;
    }, {});
    const positionData = Object.entries(positionCounts).map(([key, value]) => ({
        name: roleLabelMap[key] || key,
        value
    }));

    const pieColors = ["#8cd9b3", "#b39ddb", "#ffbb28", "#ff8042"];

    return (
        <div className="chart-container">
            <div className="chart-card">
                <h3 className="chart-title">Staff by Department</h3>
                <ResponsiveContainer width="100%" height={300}>
                    <BarChart data={departmentData}>
                        <XAxis dataKey="name" stroke="#fff" />
                        <YAxis stroke="#fff" tickFormatter={tick => Number.isInteger(tick) ? tick : ''} />
                        <Tooltip />
                        {departmentData.map((entry, index) => (
                            <Bar
                                key={`bar-${index}`}
                                dataKey="value"
                                fill={barColors[index % barColors.length]}
                                xAxisId={0}
                                barSize={40}
                            />
                        ))}
                    </BarChart>
                </ResponsiveContainer>
            </div>

            <div className="chart-card">
                <h3 className="chart-title">Staff by Role</h3>
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
                                <Cell key={`cell-${index}`} fill={pieColors[index % pieColors.length]} />
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
