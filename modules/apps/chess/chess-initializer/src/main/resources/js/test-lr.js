// import fetch from 'node-fetch';

const fetch = (...args) => import('node-fetch').then(({default: fetch}) => fetch(...args));

async function start() {
    // Construct Liferay API URL for structured-content
//     const apiUrl = `http://localhost:8080/o/api`
    const apiUrl = `http://localhost:8080/o/headless-delivery/v1.0/sites/20123/structured-contents/`
//     const apiUrl = `http://${configOptions.host}/o/headless-delivery/v1.0/sites/${configOptions.siteId}/structured-contents/`

    const init = {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'Authorization': `Basic dGVzdEBsaWZlcmF5LmNvbTp0ZXN0`
        }
    }

	const response2 = await fetch(apiUrl, init);
	const data2 = await response2.json();

	console.log(data2);
}

start();

//  echo -n "test@liferay.com:test" | base64
// dGVzdEBsaWZlcmF5LmNvbTp0ZXN0