import axios from "axios";
const BASE_URL = "http://localhost:8080/api/v1/votes"; //後端地址

export default {
  getVotes() {
    return axios.get(BASE_URL);
  },

  getVoteById(id) {
    return axios.get(`${BASE_URL}/${id}`);
  },

  addVote(vote) {
    return axios.post(`${BASE_URL}/add`, vote);
  },

  updateVote(vote) {
    return axios.put(`${BASE_URL}/update`, vote);
  },

  deleteVote(id) {
    return axios.delete(`${BASE_URL}/delete/${id}`);
  },

  voting(voterName, ids) {
    return axios.post(`${BASE_URL}/voting`, ids, {
      params: {
        voterName,
      },
    });
  },

  getVoteRecords() {
    return axios.get(`${BASE_URL}/voteRecords`);
  },
};
