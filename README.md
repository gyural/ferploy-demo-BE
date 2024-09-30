프론트

import React from 'react';

const Dashboard = () => {
  const clientId = '';
  const redirectUri = 'http://localhost:8080/auth/login/oauth/google';

  const handleGoogleLogin = () => {
    const googleAuthUrl = '';
    const scope = 'https://www.googleapis.com/auth/userinfo.email https://www.googleapis.com/auth/userinfo.profile';

    const params = new URLSearchParams({
      client_id: clientId,
      redirect_uri: redirectUri,
      response_type: 'code',
      scope: scope,
      access_type: 'offline', // 필요한 경우 refresh token을 받기 위해
      prompt: 'consent',
    });

    window.location.href = `${googleAuthUrl}?${params.toString()}`;
  };

  return (
    <div className="flex items-center justify-center h-screen bg-gray-100">
      <button
        onClick={handleGoogleLogin}
        className="px-4 py-2 font-bold text-white bg-blue-500 rounded hover:bg-blue-700 focus:outline-none focus:shadow-outline"
      >
        Google로 로그인
      </button>
    </div>
  );
};

export default Dashboard;


DB docker-compose

services:
  postgres:
    image: postgres:15
    restart: always
    volumes:
      - ./postgres-data:/var/lib/postgresql/data
    ports:
      - "5432:5432"
    environment:
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: postgres
      POSTGRES_DB: postgre
