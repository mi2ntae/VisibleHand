import React from 'react';

export default function ScrapButtonComponent() {
    return (
        <button className='goBtn' onClick={() => this.props.navigate('/news')}>
        <p>Find!</p>
        <svg
            stroke-width="4"
            stroke="currentColor"
            viewBox="0 0 24 24"s
            fill="none"
            class="h-6 w-6"
            xmlns="http://www.w3.org/2000/svg"
        >
            <path
            d="M14 5l7 7m0 0l-7 7m7-7H3"
            stroke-linejoin="round"
            stroke-linecap="round"
            ></path>
        </svg>
    </button>
    );
}