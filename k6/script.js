import http from 'k6/http';
import { check } from 'k6';
import { Rate } from 'k6/metrics';

export const errorRate = new Rate('errors');

export default function () {
    const url = 'http://127.0.0.1:52989/swagger-ui/index.html';
    const params = {
        headers: {
            'Content-Type': 'application/json',
        },
    };
    check(http.get(url, params), {
        'status is 200': (r) => r.status === 200,
    }) || errorRate.add(1);
}
