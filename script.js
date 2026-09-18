/* =========================================================
   HYDROGUARD
   INTERACTIVE MONITORING ENGINE
   JAVA BACKEND CONNECTED VERSION
========================================================= */


/* =========================================================
   JAVA BACKEND CONNECTION
========================================================= */

async function callBackend(endpoint) {

    try {

        const response = await fetch(endpoint);

        if (!response.ok) {
            throw new Error("Backend request failed");
        }

        const data = await response.json();

        console.log(
            "Java Backend Response:",
            data
        );

        return data;

    } catch (error) {

        console.error(
            "Backend Error:",
            error
        );

        showMessage(
            "Java backend is not connected."
        );

        return null;
    }
}


/* =========================================================
   1. GET HTML ELEMENTS
========================================================= */

const clock =
    document.getElementById("clock");

const flow =
    document.getElementById("flow");

const pressure =
    document.getElementById("pressure");

const production =
    document.getElementById("production");

const water =
    document.getElementById("water");

const mapFlow =
    document.getElementById("mapFlow");

const confidence =
    document.getElementById("confidence");

const confidenceBar =
    document.getElementById("confidenceBar");

const flowScore =
    document.getElementById("flowScore");

const pressureScore =
    document.getElementById("pressureScore");

const productionScore =
    document.getElementById("productionScore");

const flowSignalBar =
    document.getElementById("flowSignalBar");

const pressureSignalBar =
    document.getElementById("pressureSignalBar");

const productionSignalBar =
    document.getElementById("productionSignalBar");

const locationText =
    document.getElementById("location");

const loss =
    document.getElementById("loss");

const dailyLoss =
    document.getElementById("dailyLoss");

const money =
    document.getElementById("money");

const zoneC =
    document.getElementById("zoneC");

const zoneStatus =
    document.getElementById("zoneStatus");

const zoneIndicator =
    document.getElementById("zoneIndicator");

const alertBox =
    document.getElementById("alertBox");

const alertTitle =
    document.getElementById("alertTitle");

const alertText =
    document.getElementById("alertText");

const stateIcon =
    document.getElementById("stateIcon");

const analysisTitle =
    document.getElementById("analysisTitle");

const aiStatus =
    document.getElementById("aiStatus");

const systemStatusText =
    document.getElementById("systemStatusText");

const systemStatus =
    document.getElementById("systemStatus");

const normalBtn =
    document.getElementById("normalBtn");

const leakBtn =
    document.getElementById("leakBtn");

const startMonitoring =
    document.getElementById("startMonitoring");

const learnMore =
    document.getElementById("learnMore");


/* =========================================================
   2. LIVE CLOCK
========================================================= */

function updateClock() {

    const now =
        new Date();

    const hours =
        String(
            now.getHours()
        ).padStart(2, "0");

    const minutes =
        String(
            now.getMinutes()
        ).padStart(2, "0");

    const seconds =
        String(
            now.getSeconds()
        ).padStart(2, "0");

    clock.textContent =
        `${hours}:${minutes}:${seconds}`;
}


updateClock();

setInterval(
    updateClock,
    1000
);


/* =========================================================
   3. NUMBER ANIMATION
========================================================= */

function animateNumber(
    element,
    start,
    end,
    duration = 800,
    decimals = 0
) {

    if (!element) return;

    const startTime =
        performance.now();

    function update(currentTime) {

        const progress =
            Math.min(
                (currentTime - startTime) /
                duration,
                1
            );

        const eased =
            1 -
            Math.pow(
                1 - progress,
                3
            );

        const value =
            start +
            (end - start) *
            eased;

        element.textContent =
            decimals > 0
                ? value.toFixed(decimals)
                : Math.round(
                    value
                ).toLocaleString();

        if (progress < 1) {

            requestAnimationFrame(
                update
            );

        }

    }

    requestAnimationFrame(
        update
    );
}


/* =========================================================
   4. SET SIGNAL BAR
========================================================= */

function setSignal(
    textElement,
    barElement,
    value
) {

    if (!textElement || !barElement) {
        return;
    }

    textElement.textContent =
        `${value}%`;

    barElement.style.width =
        `${value}%`;
}


/* =========================================================
   5. NORMAL MODE
========================================================= */

function normalMode(
    data = null
) {

    /* -----------------------------------------------------
       NORMAL VALUES FROM JAVA BACKEND
    ----------------------------------------------------- */

    const newFlow =
        data && data.flow !== undefined
            ? data.flow
            : 540;

    const newPressure =
        data && data.pressure !== undefined
            ? data.pressure
            : 4.3;

    const newProduction =
        data && data.production !== undefined
            ? data.production
            : 52;

    const newWater =
        data && data.water !== undefined
            ? data.water
            : 32400;

    const newConfidence =
        data && data.confidence !== undefined
            ? data.confidence
            : 8;


    /* -----------------------------------------------------
       REMOVE LEAK MODE
    ----------------------------------------------------- */

    document.body.classList.remove(
        "leak-mode"
    );

    document.body.classList.remove(
        "emergency-mode"
    );


    zoneC.classList.remove(
        "leaking"
    );

    zoneC.classList.remove(
        "isolated"
    );


    /* -----------------------------------------------------
       SYSTEM STATUS
    ----------------------------------------------------- */

    systemStatusText.textContent =
        "NORMAL";

    systemStatus.classList.remove(
        "danger"
    );


    /* -----------------------------------------------------
       AI STATUS
    ----------------------------------------------------- */

    analysisTitle.textContent =
        "NETWORK NORMAL";

    stateIcon.textContent =
        "✓";

    aiStatus.textContent =
        "● ANALYZING";


    /* -----------------------------------------------------
       ALERT
    ----------------------------------------------------- */

    alertBox.classList.remove(
        "danger"
    );

    alertTitle.textContent =
        "SYSTEM OPERATING NORMALLY";

    alertText.textContent =
        "Water consumption is consistent with current production activity.";


    /* -----------------------------------------------------
       ZONE
    ----------------------------------------------------- */

    zoneStatus.textContent =
        "Cooling";

    zoneIndicator.textContent =
        "●";


    /* -----------------------------------------------------
       METRICS
    ----------------------------------------------------- */

    animateNumber(
        flow,
        780,
        newFlow,
        900
    );

    animateNumber(
        pressure,
        3.2,
        newPressure,
        900,
        1
    );

    animateNumber(
        production,
        52,
        newProduction,
        500
    );

    animateNumber(
        water,
        38420,
        newWater,
        1000
    );


    /* -----------------------------------------------------
       NETWORK FLOW
    ----------------------------------------------------- */

    mapFlow.textContent =
        `${newFlow} L/min`;


    /* -----------------------------------------------------
       CONFIDENCE
    ----------------------------------------------------- */

    animateNumber(
        confidence,
        87,
        newConfidence,
        900
    );

    confidenceBar.style.width =
        `${newConfidence}%`;


    /* -----------------------------------------------------
       SIGNALS
    ----------------------------------------------------- */

    setSignal(
        flowScore,
        flowSignalBar,
        data && data.flowScore !== undefined
            ? data.flowScore
            : 12
    );

    setSignal(
        pressureScore,
        pressureSignalBar,
        data && data.pressureScore !== undefined
            ? data.pressureScore
            : 9
    );

    setSignal(
        productionScore,
        productionSignalBar,
        data && data.productionScore !== undefined
            ? data.productionScore
            : 6
    );


    /* -----------------------------------------------------
       IMPACT
    ----------------------------------------------------- */

    locationText.textContent =
        "None";

    loss.textContent =
        "18 L/min";

    dailyLoss.textContent =
        "2,592 L";

    money.textContent =
        "₹156/day";


    /* -----------------------------------------------------
       BUTTON FEEDBACK
    ----------------------------------------------------- */

    buttonFeedback(
        normalBtn
    );

}


/* =========================================================
   6. SIMULATE LEAK
========================================================= */

function simulateLeak(
    data = null
) {

    /* -----------------------------------------------------
       READ JAVA BACKEND VALUES
    ----------------------------------------------------- */

    const newFlow =
        data && data.flow !== undefined
            ? data.flow
            : 780;

    const newPressure =
        data && data.pressure !== undefined
            ? data.pressure
            : 3.2;

    const newProduction =
        data && data.production !== undefined
            ? data.production
            : 52;

    const newWater =
        data && data.water !== undefined
            ? data.water
            : 38420;

    const backendConfidence =
        data && data.confidence !== undefined
            ? data.confidence
            : 87;

    const backendZone =
        data && data.zone !== undefined
            ? data.zone
            : "ZONE C";


    /* -----------------------------------------------------
       ACTIVATE LEAK THEME
    ----------------------------------------------------- */

    document.body.classList.add(
        "leak-mode"
    );

    document.body.classList.remove(
        "emergency-mode"
    );


    /* -----------------------------------------------------
       ZONE C
    ----------------------------------------------------- */

    zoneC.classList.add(
        "leaking"
    );

    zoneC.classList.remove(
        "isolated"
    );


    zoneStatus.textContent =
        "LEAK DETECTED";

    zoneIndicator.textContent =
        "⚠";


    /* -----------------------------------------------------
       SYSTEM
    ----------------------------------------------------- */

    systemStatusText.textContent =
        "ANOMALY";


    /* -----------------------------------------------------
       AI
    ----------------------------------------------------- */

    analysisTitle.textContent =
        "PROBABLE LEAK DETECTED";

    stateIcon.textContent =
        "⚠";

    aiStatus.textContent =
        "● THREAT DETECTED";


    /* -----------------------------------------------------
       ALERT
    ----------------------------------------------------- */

    alertBox.classList.add(
        "danger"
    );

    alertTitle.textContent =
        "PROBABLE LEAK DETECTED";

    alertText.textContent =
        "Water consumption increased while production remained almost unchanged. Flow and pressure signals indicate a probable leak in the cooling pipeline.";


    /* -----------------------------------------------------
       METRICS
    ----------------------------------------------------- */

    animateNumber(
        flow,
        540,
        newFlow,
        1000
    );

    animateNumber(
        pressure,
        4.3,
        newPressure,
        1000,
        1
    );

    animateNumber(
        production,
        52,
        newProduction,
        500
    );

    animateNumber(
        water,
        32400,
        newWater,
        1000
    );


    /* -----------------------------------------------------
       NETWORK FLOW
    ----------------------------------------------------- */

    mapFlow.textContent =
        `${newFlow} L/min`;


    /* -----------------------------------------------------
       CONFIDENCE
    ----------------------------------------------------- */

    animateNumber(
        confidence,
        8,
        backendConfidence,
        1000
    );

    confidenceBar.style.width =
        `${backendConfidence}%`;


    /* -----------------------------------------------------
       SIGNALS
    ----------------------------------------------------- */

    setSignal(
        flowScore,
        flowSignalBar,
        data && data.flowScore !== undefined
            ? data.flowScore
            : 92
    );

    setSignal(
        pressureScore,
        pressureSignalBar,
        data && data.pressureScore !== undefined
            ? data.pressureScore
            : 81
    );

    setSignal(
        productionScore,
        productionSignalBar,
        data && data.productionScore !== undefined
            ? data.productionScore
            : 88
    );


    /* -----------------------------------------------------
       IMPACT
    ----------------------------------------------------- */

    locationText.textContent =
        `${backendZone} • COOLING`;

    loss.textContent =
        "240 L/min";

    dailyLoss.textContent =
        "34,560 L";

    money.textContent =
        "₹2,074/day";


    /* -----------------------------------------------------
       BUTTON FEEDBACK
    ----------------------------------------------------- */

    buttonFeedback(
        leakBtn
    );

}


/* =========================================================
   7. ISOLATE ZONE
========================================================= */

async function isolateZone() {

    /* -----------------------------------------------------
       ONLY ALLOW AFTER LEAK
    ----------------------------------------------------- */

    if (
        !zoneC.classList.contains(
            "leaking"
        )
    ) {

        showMessage(
            "No active leak detected. Zone isolation is not required."
        );

        return;

    }


    /* -----------------------------------------------------
       CALL JAVA BACKEND
    ----------------------------------------------------- */

    const data =
        await callBackend(
            "/api/isolate"
        );


    if (!data) {

        return;

    }


    /* -----------------------------------------------------
       MARK ZONE AS ISOLATED
    ----------------------------------------------------- */

    zoneC.classList.add(
        "isolated"
    );


    zoneStatus.textContent =
        "ISOLATED";

    zoneIndicator.textContent =
        "🔒";


    /* -----------------------------------------------------
       UPDATE ALERT
    ----------------------------------------------------- */

    alertTitle.textContent =
        "ZONE C ISOLATED";

    alertText.textContent =
        "Simulated isolation command activated. Water flow to the affected zone has been restricted while maintenance response is initiated.";


    /* -----------------------------------------------------
       GET BACKEND VALUES
    ----------------------------------------------------- */

    const isolatedFlow =
        data.flow !== undefined
            ? data.flow
            : 610;

    const isolatedLoss =
        data.loss !== undefined
            ? data.loss
            : 35;


    const isolatedDailyLoss =
        data.dailyLoss !== undefined
            ? data.dailyLoss
            : 4608;


    const isolatedMoney =
        data.financialLoss !== undefined
            ? data.financialLoss
            : 276;


    const isolatedConfidence =
        data.confidence !== undefined
            ? data.confidence
            : 42;


    /* -----------------------------------------------------
       REDUCE FLOW
    ----------------------------------------------------- */

    animateNumber(
        flow,
        780,
        isolatedFlow,
        1000
    );


    mapFlow.textContent =
        `${isolatedFlow} L/min`;


    /* -----------------------------------------------------
       REDUCE LOSS
    ----------------------------------------------------- */

    loss.textContent =
        `${isolatedLoss} L/min`;

    dailyLoss.textContent =
        `${Number(
            isolatedDailyLoss
        ).toLocaleString()} L`;

    money.textContent =
        `₹${Number(
            isolatedMoney
        ).toLocaleString()}/day`;


    /* -----------------------------------------------------
       CONFIDENCE
    ----------------------------------------------------- */

    animateNumber(
        confidence,
        87,
        isolatedConfidence,
        700
    );

    confidenceBar.style.width =
        `${isolatedConfidence}%`;


    /* -----------------------------------------------------
       AI STATE
    ----------------------------------------------------- */

    analysisTitle.textContent =
        "LEAK CONTAINED";

    stateIcon.textContent =
        "🔒";

    aiStatus.textContent =
        "● CONTAINMENT ACTIVE";


    /* -----------------------------------------------------
       SYSTEM STATUS
    ----------------------------------------------------- */

    systemStatusText.textContent =
        "CONTAINED";


    /* -----------------------------------------------------
       MESSAGE
    ----------------------------------------------------- */

    showMessage(
        "ZONE C ISOLATION SIMULATED"
    );

}


/* =========================================================
   8. EMERGENCY RESPONSE
========================================================= */

async function emergencyResponse() {

    /* -----------------------------------------------------
       CALL JAVA BACKEND
    ----------------------------------------------------- */

    const data =
        await callBackend(
            "/api/emergency"
        );


    if (!data) {

        return;

    }


    /* -----------------------------------------------------
       EMERGENCY VISUAL
    ----------------------------------------------------- */

    document.body.classList.add(
        "emergency-mode"
    );


    /* -----------------------------------------------------
       AI
    ----------------------------------------------------- */

    analysisTitle.textContent =
        "EMERGENCY RESPONSE ACTIVE";

    stateIcon.textContent =
        "🚨";

    aiStatus.textContent =
        "● RESPONSE ACTIVE";


    /* -----------------------------------------------------
       ALERT
    ----------------------------------------------------- */

    alertBox.classList.add(
        "danger"
    );


    alertTitle.textContent =
        "EMERGENCY RESPONSE ACTIVATED";


    alertText.textContent =
        "Simulated safety protocol activated. Maintenance team notification and containment workflow have been initiated.";


    /* -----------------------------------------------------
       CONFIDENCE
    ----------------------------------------------------- */

    const emergencyConfidence =
        data.confidence !== undefined
            ? data.confidence
            : 97;


    confidence.textContent =
        `${emergencyConfidence}%`;

    confidenceBar.style.width =
        `${emergencyConfidence}%`;


    /* -----------------------------------------------------
       MESSAGE
    ----------------------------------------------------- */

    showMessage(
        "EMERGENCY RESPONSE PROTOCOL ACTIVATED"
    );


    /* -----------------------------------------------------
       REMOVE EMERGENCY VISUAL
    ----------------------------------------------------- */

    setTimeout(() => {

        document.body.classList.remove(
            "emergency-mode"
        );

    }, 5000);

}


/* =========================================================
   9. BUTTON FEEDBACK
========================================================= */

function buttonFeedback(
    button
) {

    if (!button) return;


    button.classList.remove(
        "clicked"
    );


    void button.offsetWidth;


    button.classList.add(
        "clicked"
    );


    setTimeout(() => {

        button.classList.remove(
            "clicked"
        );

    }, 500);

}


/* =========================================================
   10. MESSAGE POPUP
========================================================= */

function showMessage(
    message
) {

    const old =
        document.querySelector(
            ".toast-message"
        );


    if (old) {

        old.remove();

    }


    const toast =
        document.createElement(
            "div"
        );


    toast.className =
        "toast-message";


    toast.textContent =
        message;


    document.body.appendChild(
        toast
    );


    setTimeout(() => {

        toast.classList.add(
            "show"
        );

    }, 50);


    setTimeout(() => {

        toast.classList.remove(
            "show"
        );


        setTimeout(() => {

            toast.remove();

        }, 400);

    }, 3000);

}


/* =========================================================
   11. CREATE EXTRA CONTROL BUTTONS
========================================================= */

function createExtraControls() {

    const actionArea =
        document.querySelector(
            ".action-buttons"
        );


    if (!actionArea) return;


    /* -----------------------------------------------------
       ISOLATE BUTTON
    ----------------------------------------------------- */

    const isolate =
        document.createElement(
            "button"
        );


    isolate.className =
        "control-button isolate-button";


    isolate.innerHTML =
        "<span>🔒</span> ISOLATE ZONE C";


    isolate.addEventListener(
        "click",
        isolateZone
    );


    /* -----------------------------------------------------
       EMERGENCY BUTTON
    ----------------------------------------------------- */

    const emergency =
        document.createElement(
            "button"
        );


    emergency.className =
        "control-button emergency-button";


    emergency.innerHTML =
        "<span>🚨</span> EMERGENCY RESPONSE";


    emergency.addEventListener(
        "click",
        emergencyResponse
    );


    /* -----------------------------------------------------
       ADD BUTTONS
    ----------------------------------------------------- */

    actionArea.appendChild(
        isolate
    );

    actionArea.appendChild(
        emergency
    );

}


/* =========================================================
   12. START MONITORING
========================================================= */

function startSystem() {

    showMessage(
        "HYDROGUARD MONITORING STARTED"
    );


    startMonitoring.innerHTML =
        "<span>✓</span> MONITORING ACTIVE";


    startMonitoring.style.pointerEvents =
        "none";


    startMonitoring.style.opacity =
        "0.7";

}


/* =========================================================
   13. HOW IT WORKS BUTTON
========================================================= */

function scrollToHowItWorks() {

    const section =
        document.getElementById(
            "howItWorks"
        );


    if (section) {

        section.scrollIntoView({
            behavior: "smooth"
        });

    }

}


/* =========================================================
   14. EVENT LISTENERS
========================================================= */


/* ---------------------------------------------------------
   NORMAL BUTTON
--------------------------------------------------------- */

normalBtn.addEventListener(
    "click",
    async () => {

        const data =
            await callBackend(
                "/api/normal"
            );


        if (data) {

            normalMode(
                data
            );

        }

    }
);


/* ---------------------------------------------------------
   LEAK BUTTON
--------------------------------------------------------- */

leakBtn.addEventListener(
    "click",
    async () => {

        const data =
            await callBackend(
                "/api/leak"
            );


        if (data) {

            simulateLeak(
                data
            );

        }

    }
);


/* ---------------------------------------------------------
   START MONITORING
--------------------------------------------------------- */

startMonitoring.addEventListener(
    "click",
    startSystem
);


/* ---------------------------------------------------------
   LEARN MORE
--------------------------------------------------------- */

learnMore.addEventListener(
    "click",
    scrollToHowItWorks
);


/* =========================================================
   15. INITIALIZE
========================================================= */

createExtraControls();

normalMode();


console.log(
    "HydroGuard monitoring engine initialized."
);

console.log(
    "Java backend integration enabled."
);