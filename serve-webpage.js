#!/usr/bin/env node

/**
 * Simple HTTP Server for Hospital Management System Frontend
 * Serves static files from /app/webpage directory
 * Run with: node serve-webpage.js
 */

const http = require('http');
const fs = require('fs');
const path = require('path');

const PORT = 3000;
const WEBPAGE_DIR = path.join(__dirname, 'webpage');

// MIME types
const mimeTypes = {
    '.html': 'text/html',
    '.css': 'text/css',
    '.js': 'text/javascript',
    '.json': 'application/json',
    '.png': 'image/png',
    '.jpg': 'image/jpeg',
    '.jpeg': 'image/jpeg',
    '.gif': 'image/gif',
    '.svg': 'image/svg+xml',
    '.ico': 'image/x-icon',
    '.woff': 'font/woff',
    '.woff2': 'font/woff2',
    '.ttf': 'font/ttf',
    '.otf': 'font/otf'
};

const server = http.createServer((req, res) => {
    console.log(`${new Date().toISOString()} - ${req.method} ${req.url}`);

    // Parse URL
    let filePath = req.url === '/' ? '/Main.html' : req.url;
    
    // Remove query parameters
    filePath = filePath.split('?')[0];
    
    // Security: prevent directory traversal
    filePath = path.normalize(filePath).replace(/^(\.\.[\/\\])+/, '');
    
    // Full file path
    const fullPath = path.join(WEBPAGE_DIR, filePath);

    // Check if file exists
    fs.access(fullPath, fs.constants.F_OK, (err) => {
        if (err) {
            // File not found
            res.writeHead(404, { 'Content-Type': 'text/html' });
            res.end('<h1>404 Not Found</h1><p>The requested file was not found.</p>');
            console.log(`  └─ 404: File not found - ${fullPath}`);
            return;
        }

        // Get file extension
        const ext = path.extname(fullPath).toLowerCase();
        const contentType = mimeTypes[ext] || 'application/octet-stream';

        // Read and serve file
        fs.readFile(fullPath, (err, data) => {
            if (err) {
                res.writeHead(500, { 'Content-Type': 'text/html' });
                res.end('<h1>500 Internal Server Error</h1><p>Error reading file.</p>');
                console.log(`  └─ 500: Error reading file - ${err.message}`);
                return;
            }

            // Success
            res.writeHead(200, { 
                'Content-Type': contentType,
                'Cache-Control': 'no-cache'
            });
            res.end(data);
            console.log(`  └─ 200: Served successfully`);
        });
    });
});

server.listen(PORT, () => {
    console.log('='.repeat(60));
    console.log('Hospital Management System - Frontend Server');
    console.log('='.repeat(60));
    console.log(`Server running at: http://localhost:${PORT}/`);
    console.log(`Serving files from: ${WEBPAGE_DIR}`);
    console.log(`\nOpen in browser: http://localhost:${PORT}/Main.html`);
    console.log('\nPress Ctrl+C to stop the server');
    console.log('='.repeat(60));
});

// Graceful shutdown
process.on('SIGINT', () => {
    console.log('\n\nShutting down server...');
    server.close(() => {
        console.log('Server stopped.');
        process.exit(0);
    });
});
