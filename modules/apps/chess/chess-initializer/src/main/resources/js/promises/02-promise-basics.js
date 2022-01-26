let promise = new Promise(
	function(resolve, reject) {
		// executor
		// resolve(value);
		// reject(error);
		setTimeout(
// 			() => resolve("done"),
			() => reject("error"),
			1000
		);
	}
);

// The executor is called automatically and immediately (by new Promise).

// promise.then(
// 	function(result) {
// 		console.log("result: " + result);
// 	},
// 	function(error) {
// 		console.log("error: " + error);
// 	},
// );

promise.catch(
	function(error) {
		console.log("Error caught: " + error);
	}
).finally(
	function() {
		console.log("Promise is settled now");
	}
);

let inmediatePromise = new Promise(
	function(resolve, reject) {
		resolve("Finished");
	}
);

inmediatePromise.then(console.log);

function loadScript(src, callback) {
  let script = document.createElement('script');
  script.src = src;

  script.onload = () => callback(null, script);
  script.onerror = () => callback(new Error(`Script load error for ${src}`));

  document.head.append(script);
}

function loadScriptWithPromises(src) {
	return new Promise(
		function(resolve, reject) {
			let script = document.createElement('script');
			script.src = src;

			script.onload = () => resolve(script);
			script.onerror = () => reject(new Error(`Script load error for ${src}`));

			document.head.append(script);

			console.log("loadScriptWithPromises finished");
		}
	);
}

loadScriptWithPromises( './01-callbacks-script.js');


// Promise
// state: "pending" -> "fulfilled" / "rejected"
// result: undefined -> value / error
//
// state and result are internal