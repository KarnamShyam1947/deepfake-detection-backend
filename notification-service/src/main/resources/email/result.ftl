<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.0/css/all.min.css" integrity="sha512-DxV+EoADOkOygM4IR9yXP8Sb2qwgidEmeqAEmDKIOfPRQZOWbXCzLC6vjbZyy0vPisbH2SyW27+ddLVCN+OMzQ==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    <title>DeepDetect AI - Analysis Complete</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
            line-height: 1.6;
            color: #1e293b;
            background: linear-gradient(135deg, #3b82f6, #8b5cf6);
            margin: 0;
            padding: 20px;
            min-height: 100vh;
        }

        .email-container {
            max-width: 1200px;
            width: 100%;
            margin: 0 auto;
            background: rgba(255, 255, 255, 0.95);
            backdrop-filter: blur(20px);
            border-radius: 24px;
            border: 1px solid rgba(255, 255, 255, 0.2);
            box-shadow: 0 25px 50px -12px rgba(0, 0, 0, 0.25);
            overflow: hidden;
        }

        .header {
            background: linear-gradient(135deg, #3b82f6, #8b5cf6);
            color: white;
            text-align: center;
            padding: 40px 20px;
        }

        .logo {
            font-size: 28px;
            font-weight: bold;
            margin-bottom: 8px;
            background: linear-gradient(135deg, #ffffff, #e2e8f0);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
            background-clip: text;
        }

        .header-subtitle {
            font-size: 16px;
            opacity: 0.9;
        }

        .content {
            padding: 40px 30px;
        }

        .greeting {
            font-size: 18px;
            margin-bottom: 24px;
            color: #1e293b;
        }

        .analysis-card {
            background: rgba(255, 255, 255, 0.8);
            border-radius: 16px;
            padding: 30px;
            margin: 24px 0;
            border: 1px solid rgba(229, 231, 235, 0.8);
            box-shadow: 0 10px 25px -10px rgba(0, 0, 0, 0.1);
        }

        .analysis-header {
            display: flex;
            justify-content: space-between;
            align-items: flex-start;
            margin-bottom: 24px;
            flex-wrap: wrap;
            gap: 16px;
        }

        .analysis-title {
            font-size: 24px;
            font-weight: 600;
            color: #1e293b;
            margin-bottom: 8px;
        }

        .request-id {
            font-size: 14px;
            color: #64748b;
        }

        .confidence-score {
            text-align: right;
        }

        .confidence-number {
            font-size: 36px;
            font-weight: bold;
            margin-bottom: 4px;
        }

        .confidence-label {
            font-size: 14px;
            color: #64748b;
        }

        .result-box {
            text-align: center;
            padding: 24px;
            border-radius: 12px;
            margin: 20px 0;
        }

        .result-authentic {
            background: rgba(34, 197, 94, 0.1);
            border: 2px solid rgba(34, 197, 94, 0.2);
        }

        .result-deepfake {
            background: rgba(239, 68, 68, 0.1);
            border: 2px solid rgba(239, 68, 68, 0.2);
        }

        .result-title {
            font-size: 32px;
            font-weight: bold;
            margin-bottom: 12px;
        }

        .result-title.authentic {
            color: #22c55e;
        }

        .result-title.deepfake {
            color: #ef4444;
        }

        .result-description {
            font-size: 16px;
            color: #64748b;
            line-height: 1.5;
        }

        .stats-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(150px, 1fr));
            gap: 20px;
            margin: 30px 0;
        }

        .stat-card {
            background: rgba(255, 255, 255, 0.6);
            border-radius: 12px;
            padding: 20px;
            text-align: center;
            border: 1px solid rgba(229, 231, 235, 0.5);
        }

        .stat-number {
            font-size: 24px;
            font-weight: bold;
            color: #3b82f6;
            margin-bottom: 8px;
        }

        .stat-label {
            font-size: 14px;
            color: #64748b;
        }

        .cta-button {
            display: inline-block;
            background: linear-gradient(135deg, #3b82f6, #8b5cf6);
            color: white;
            text-decoration: none;
            padding: 14px 28px;
            border-radius: 8px;
            font-weight: 600;
            margin: 20px 8px 8px 0;
            transition: transform 0.2s ease;
        }

        .cta-button:hover {
            transform: translateY(-2px);
        }

        .secondary-button {
            display: inline-block;
            background: transparent;
            color: #3b82f6;
            text-decoration: none;
            padding: 14px 28px;
            border: 2px solid #3b82f6;
            border-radius: 8px;
            font-weight: 600;
            margin: 8px 8px 8px 0;
            transition: all 0.2s ease;
        }

        .secondary-button:hover {
            background: #3b82f6;
            color: white;
            transform: translateY(-2px);
        }

        .footer {
            background: #f8fafc;
            padding: 30px;
            text-align: center;
            color: #64748b;
            font-size: 14px;
            border-top: 1px solid #e2e8f0;
        }

        .footer-links {
            margin: 16px 0;
        }

        .footer-links a {
            color: #3b82f6;
            text-decoration: none;
            margin: 0 12px;
        }

        .file-info {
            background: rgba(255, 255, 255, 0.5);
            border-radius: 12px;
            padding: 20px;
            margin: 20px 0;
            border: 1px solid rgba(229, 231, 235, 0.5);
        }

        .file-info-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(120px, 1fr));
            gap: 16px;
            margin-top: 16px;
        }

        .file-info-item {
            text-align: center;
        }

        .file-info-label {
            font-size: 12px;
            color: #64748b;
            margin-bottom: 4px;
            text-transform: uppercase;
            letter-spacing: 0.5px;
        }

        .file-info-value {
            font-size: 14px;
            font-weight: 600;
            color: #1e293b;
        }

        .status-badge {
            display: inline-block;
            padding: 6px 12px;
            border-radius: 20px;
            font-size: 12px;
            font-weight: 600;
            text-transform: uppercase;
            letter-spacing: 0.5px;
        }

        .status-completed {
            background: rgba(34, 197, 94, 0.1);
            color: #22c55e;
            border: 1px solid rgba(34, 197, 94, 0.2);
        }

        .status-processing {
            background: rgba(245, 158, 11, 0.1);
            color: #f59e0b;
            border: 1px solid rgba(245, 158, 11, 0.2);
        }

        .status-failed {
            background: rgba(239, 68, 68, 0.1);
            color: #ef4444;
            border: 1px solid rgba(239, 68, 68, 0.2);
        }

        @media (max-width: 768px) {
            body {
                padding: 16px;
            }

            .email-container {
                border-radius: 16px;
            }

            .header {
                padding: 30px 20px;
            }

            .logo {
                font-size: 24px;
            }

            .header-subtitle {
                font-size: 14px;
            }

            .content {
                padding: 30px 20px;
            }

            .analysis-card {
                padding: 20px;
            }

            .analysis-header {
                flex-direction: column;
                text-align: center;
                gap: 20px;
            }

            .confidence-score {
                text-align: center;
            }

            .confidence-number {
                font-size: 28px;
            }

            .analysis-title {
                font-size: 20px;
            }

            .result-title {
                font-size: 24px;
            }

            .stats-grid {
                grid-template-columns: 1fr;
                gap: 16px;
            }

            .file-info-grid {
                grid-template-columns: 1fr 1fr;
                gap: 12px;
            }

            .cta-button,
            .secondary-button {
                display: block;
                text-align: center;
                margin: 12px 0;
                padding: 16px 24px;
            }

            .footer {
                padding: 24px 20px;
            }
        }

        @media (max-width: 480px) {
            body {
                padding: 12px;
            }

            .email-container {
                border-radius: 12px;
            }

            .header {
                padding: 24px 16px;
            }

            .content {
                padding: 24px 16px;
            }

            .analysis-card {
                padding: 16px;
            }

            .file-info {
                padding: 16px;
            }

            .file-info-grid {
                grid-template-columns: 1fr;
                gap: 10px;
            }

            .stat-card {
                padding: 16px;
            }

            .greeting {
                font-size: 16px;
            }

            .footer {
                padding: 20px 16px;
            }

            .footer-links a {
                display: block;
                margin: 8px 0;
            }
        }
    </style>
</head>

<body>
    <div class="email-container">
        <!-- Header -->
        <div class="header">
            <div class="logo">DeepDetect AI</div>
            <div class="header-subtitle">Advanced AI-Powered Deepfake Detection</div>
        </div>

        <!-- Content -->
        <div class="content">
            <div class="greeting">
                Hello ${userName},
            </div>

            <p>Your video analysis has been completed! Our advanced AI system has finished examining your video for
                signs of manipulation.</p>

            <!-- Analysis Results Card -->
            <div class="analysis-card">
                <div class="analysis-header">
                    <div>
                        <div class="analysis-title">Analysis Complete</div>
                        <div class="request-id">Request ID: ${requestId}</div>
                        <span class="status-badge status-${status}">${status}</span>
                    </div>
                    <div class="confidence-score">
                        <div class="confidence-number">${confidence}%</div>
                        <div class="confidence-label">Confidence</div>
                    </div>
                </div>

                 <!-- Result Box -->
                <div class="result-box ${resultClass}">
                    <div class="result-title ${resultClass}">
                        ${resultIcon?no_esc} ${result}
                    </div>
                    <div class="result-description">
                        ${resultDescription}
                    </div>
                </div>
                
                <!-- File Information -->
                <div class="file-info">
                    <h4 style="margin: 0 0 16px 0; color: #1e293b; font-size: 16px;">File Details</h4>
                    <div class="file-info-grid">
                        <div class="file-info-item">
                            <div class="file-info-label">File Name</div>
                            <div class="file-info-value">${fileName}</div>
                        </div>
                        <div class="file-info-item">
                            <div class="file-info-label">File Size</div>
                            <div class="file-info-value">${fileSize}</div>
                        </div>
                        <div class="file-info-item">
                            <div class="file-info-label">Duration</div>
                            <div class="file-info-value">${duration}</div>
                        </div>
                        <div class="file-info-item">
                            <div class="file-info-label">Start Date</div>
                            <div class="file-info-value">${startDate?string('dd.MM.yyyy HH:mm:ss')}</div>
                        </div>
                        <div class="file-info-item">
                            <div class="file-info-label">End Date</div>
                            <div class="file-info-value">${endDate?string('dd.MM.yyyy HH:mm:ss')}</div>
                        </div>
                        <div class="file-info-item">
                            <div class="file-info-label">Processing Time</div>
                            <div class="file-info-value">${processingTime}</div>
                        </div>
                    </div>
                </div>
            </div>

            
            <div style="text-align: center; margin: 30px 0;">
                <a href="${originalFileUrl}" class="cta-button">Download Original File</a>
                <a href="${outputFileUrl}" class="secondary-button">Download Output File</a>
            </div>

            <p style="margin-top: 30px; color: #64748b; font-size: 14px;">
                You can access your complete analysis results, including detailed technical information and downloadable
                reports, by clicking the button above or visiting your dashboard.
            </p>
        </div>

        <!-- Footer -->
        <div class="footer">
            <div>
                <strong>DeepDetect AI</strong> - Protecting digital authenticity with advanced AI
            </div>
            <div class="footer-links">
                <a href="#">Visit Website</a>
                <a href="#">Dashboard</a>
                <a href="#">Support</a>
            </div>
            <div style="margin-top: 16px;">
                © 2024 DeepDetect AI. All rights reserved.
            </div>
        </div>
    </div>
</body>

</html>