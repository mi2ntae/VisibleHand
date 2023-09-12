import React, { Component } from 'react';
import Flippy, { FrontSide, BackSide } from 'react-flippy';
import "./MoveArticleBtn.css";
import "./CardFront.css";
import { useNavigate } from 'react-router';

export default class Memo extends Component {
    render() {
        return (
            <Flippy
                flipOnHover={true} // default false
                flipOnClick={true} // default false
                flipDirection="horizontal" // horizontal or vertical
                ref={(r) => this.flippy = r} // to use toggle method like this.
                style={{ width: '200px', height: '200px'}} 
            >
                <FrontSide style={{  backgroundColor: "#EFF4FF", padding: 0, borderRadius: "30px"}}>
                    <div className="myCard">
                        <div className="img" style={{
                            backgroundImage: `url("https://images.unsplash.com/photo-1507833423370-a126b89d394b?auto=format&fit=crop&w=90")`
                        }} >
                        </div>
                    </div>
                
                
                
                </FrontSide>
                <BackSide style={{ backgroundColor: "#EFF4FF", display: "flex", justifyContent: "center", borderRadius: "30px", alignItems: "center"}}>
                    <button className='goBtn' onClick={() => this.props.navigate('/news')}>
                        <p>Article!</p>
                        <svg
                            stroke-width="4"
                            stroke="currentColor"
                            viewBox="0 0 24 24"
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
                </BackSide>
            </Flippy>
        );
    }
}

function withNavigation(Component) {
    return props => <Component {...props} navigate={useNavigate()} />;
}