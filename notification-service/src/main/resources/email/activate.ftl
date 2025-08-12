<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Activate Your DeepDetect AI Account</title>
    <style>
        body {
            font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
            background: linear-gradient(135deg, #3b82f6, #8b5cf6);
            padding: 20px;
            margin: 0;
            min-height: 100vh;
            color: #1e293b;
        }

        .email-container {
            max-width: 600px;
            margin: 0 auto;
            background: rgba(255, 255, 255, 0.95);
            border-radius: 20px;
            overflow: hidden;
            box-shadow: 0 20px 40px rgba(0, 0, 0, 0.15);
            border: 1px solid rgba(255, 255, 255, 0.3);
            backdrop-filter: blur(10px);
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
            padding: 30px 24px;
        }

        .greeting {
            font-size: 18px;
            margin-bottom: 20px;
        }

        .message {
            font-size: 16px;
            color: #475569;
            line-height: 1.6;
            margin-bottom: 30px;
        }

        .cta-button {
            display: inline-block;
            background: linear-gradient(135deg, #3b82f6, #8b5cf6);
            color: white;
            text-decoration: none;
            padding: 14px 28px;
            border-radius: 8px;
            font-weight: 600;
            transition: transform 0.2s ease;
        }

        .cta-button:hover {
            transform: translateY(-2px);
        }

        .footer {
            background: #f8fafc;
            padding: 24px;
            text-align: center;
            font-size: 14px;
            color: #64748b;
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

        @media (max-width: 480px) {
            .content {
                padding: 24px 16px;
            }

            .cta-button {
                display: block;
                width: 100%;
                text-align: center;
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
        <div class="header">
            <div class="logo">DeepDetect AI</div>
            <div class="header-subtitle">Welcome to the Future of AI-Powered Authenticity</div>
        </div>

        <div class="content">
            <div class="greeting">Hello ${userName},</div>

            <div class="message">
                Thank you for choosing <strong>DeepDetect AI</strong>! We're excited to have you on board.
                <br /><br />
                To get started, please activate your account by clicking the button below. This activation link is valid
                for <strong>1 hour</strong> from the time you received this email.
            </div>

            <div style="text-align: center; margin-bottom: 30px;">
                <a href="${activationLink}" class="cta-button">Activate My Account</a>
            </div>

            <div class="message" style="font-size: 14px; color: #94a3b8;">
                Didn’t request this account? Just ignore this email.
            </div>
        </div>

        <div class="footer">
            <div><strong>DeepDetect AI</strong> — Protecting digital authenticity with advanced AI</div>
            <div class="footer-links">
                <a href="#">Visit Website</a>
                <a href="#">Support</a>
                <a href="#">Privacy Policy</a>
            </div>
            <div>© 2024 DeepDetect AI. All rights reserved.</div>
        </div>
    </div>
</body>

</html>
