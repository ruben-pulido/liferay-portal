// import fetch from 'node-fetch';

const fetch = (...args) => import('node-fetch').then(({default: fetch}) => fetch(...args));

async function start() {
	const response = await fetch('https://github.com/');
	const body = await response.text();

	console.log(body);

	const response2 = await fetch('https://api.github.com/users/github');
	const data2 = await response2.json();

	console.log(data2);

	const response3 = await fetch('https://httpbin.org/post', {method: 'POST', body: 'a=1'});
	const data3 = await response3.json();

	console.log(data3);

	const body4 = {a: 1};

	const response4 = await fetch('https://httpbin.org/post', {
		method: 'post',
		body: JSON.stringify(body4),
		headers: {'Content-Type': 'application/json'}
	});
	const data4 = await response4.json();

	console.log(data4);

	const params5 = new URLSearchParams();
	params5.append('a', 1);

	const response5 = await fetch('https://httpbin.org/post', {method: 'POST', body: params5});
	const data5 = await response5.json();

	console.log(data5);
}

start();