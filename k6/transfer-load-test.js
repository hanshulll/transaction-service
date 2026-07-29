import http from "k6/http";

// TODO (Week 6): sustained transfer load + duplicate idempotency-key scenario +
// concurrent-same-account scenario, combined into one script. Capture raw numbers
// into docs/PERFORMANCE.md.
export const options = {
	scenarios: {
		// sustained_transfer_load: {},
		// duplicate_idempotency_key: {},
		// concurrent_same_account: {},
	},
};

export default function () {
	throw new Error("TODO: implement k6 transfer load scenarios");
}
