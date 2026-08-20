const API = "/api";


function showError(message) {

    const error = document.getElementById("errorMessage");

    error.textContent = message;

    error.classList.remove("hidden");
}


function clearError() {

    document
        .getElementById("errorMessage")
        .classList.add("hidden");
}


async function loadCareerData() {

    clearError();

    const personId =
        document.getElementById("personId").value.trim();

    if (!personId) {

        showError("Please enter a person ID.");

        return;
    }

    try {

        await loadRecommendedJobs(personId);

        await loadSimilarProfessionals(personId);

        await loadSkillGap();

    } catch (error) {

        showError(error.message);
    }
}


async function loadRecommendedJobs(personId) {

    const response = await fetch(
        `${API}/persons/${personId}/jobs`
    );

    if (!response.ok) {

        const error = await response.json();

        throw new Error(
            error.message || "Unable to load recommended jobs."
        );
    }

    const data = await response.json();


    document.getElementById("personInfo").innerHTML = `
        <h3>${data.person}</h3>

        <p>
            Matching Skills:
        </p>

        <div>
            ${data.matchingSkills
                .map(skill =>
                    `<span class="skill matching">${skill}</span>`
                )
                .join("")}
        </div>
    `;


    document.getElementById("recommendedJobs").innerHTML =
        data.recommendedJobs
            .map(job =>
                `<div class="job">${job}</div>`
            )
            .join("");
}


async function loadSkillGap() {

    clearError();

    const personId =
        document.getElementById("personId").value.trim();

    const jobId =
        document.getElementById("jobId").value.trim();

    if (!personId || !jobId) {

        showError(
            "Please enter both Person ID and Job ID."
        );

        return;
    }

    try {

        const response = await fetch(
            `${API}/persons/${personId}/skill-gap/${jobId}`
        );

        if (!response.ok) {

            const error = await response.json();

            throw new Error(
                error.message || "Unable to load skill gap."
            );
        }

        const data = await response.json();


        document.getElementById("skillGap").innerHTML = `

            <h3>${data.job}</h3>

            <h4>Matching Skills</h4>

            <div>
                ${data.matchingSkills
                    .map(skill =>
                        `<span class="skill matching">
                            ✓ ${skill}
                        </span>`
                    )
                    .join("")}
            </div>


            <h4 style="margin-top:15px">
                Missing Skills
            </h4>

            <div>
                ${data.missingSkills
                    .map(skill =>
                        `<span class="skill missing">
                            ✗ ${skill}
                        </span>`
                    )
                    .join("")}
            </div>

        `;

    } catch (error) {

        showError(error.message);
    }
}


async function loadSimilarProfessionals(personId) {

    const response = await fetch(
        `${API}/persons/${personId}/similar-professionals`
    );

    if (!response.ok) {

        const error = await response.json();

        throw new Error(
            error.message ||
            "Unable to load similar professionals."
        );
    }

    const data = await response.json();


    document.getElementById(
        "similarProfessionals"
    ).innerHTML = data.similarProfessionals
        .map(person => `

            <div class="professional">

                <strong>
                    ${person.name}
                </strong>

                <span class="percentage">
                    ${person.similarityPercentage}%
                </span>

                <div style="margin-top:10px">

                    ${person.sharedSkills
                        .map(skill =>
                            `<span class="skill matching">
                                ${skill}
                            </span>`
                        )
                        .join("")}

                </div>

            </div>

        `)
        .join("");
}
async function loadCandidates() {

    clearError();

    const jobId =
        document.getElementById("candidateJobId").value.trim();

    if (!jobId) {

        showError("Please enter a job ID.");

        return;
    }

    try {

        const response = await fetch(
            `${API}/jobs/${jobId}/candidates`
        );

        if (!response.ok) {

            const error = await response.json();

            throw new Error(
                error.message || "Unable to load candidates."
            );
        }

        const data = await response.json();

        const candidateResults =
            document.getElementById("candidateResults");


        candidateResults.innerHTML = `

            <h3 style="margin-top: 20px;">
                ${data.job}
            </h3>

            <p style="margin-top: 8px;">
                Required Skills:
            </p>

            <div style="margin-bottom: 20px;">

                ${data.requiredSkills
                    .map(skill =>
                        `<span class="skill matching">
                            ${skill}
                        </span>`
                    )
                    .join("")}

            </div>

            ${data.candidates
                .map((candidate, index) => `

                    <div class="professional">

                        <strong>
                            #${index + 1}
                            ${candidate.name}
                        </strong>

                        <span class="percentage">
                            ${candidate.matchPercentage}%
                        </span>

                        <div style="margin-top: 10px;">

                            ${candidate.matchingSkills
                                .map(skill =>
                                    `<span class="skill matching">
                                        ${skill}
                                    </span>`
                                )
                                .join("")}

                        </div>

                    </div>

                `)
                .join("")}

        `;

    } catch (error) {

        showError(error.message);

    }
}