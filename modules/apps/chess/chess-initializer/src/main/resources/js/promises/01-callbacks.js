console.log("I am 01-callbacks-script.js");

function loadScript(src, callback) {
	let script = document.createElement('script');
	script.src = src;

	script.onload = () => callback(script);

	document.head.append(script);
}

function f2(script) {
	console.log("I am f2");
	console.log("script src: " + script.src);
	return script.src;
}

// loadScript(
// 	'/Users/rubenpulido/projects/liferay/p4/liferay-portal/modules/apps/chess/chess-initializer/src/main/resources/js/promises/01-callbacks-script.js',
// 	() => {
// 			f1();
// 		}
// );

loadScript(
	'./01-callbacks-script.js',
	(script) => {

		console.log("Hello1");

		let result = f2(script);
		console.log("Script loaded: " + result);

		loadScript(
			'./01-callbacks-script-02.js',
			() => {
				console.log("Hello2;")
			}
		)

	}


);