<script>
import { Modal } from "bootstrap";
import VoteService from "@/services/VoteService";

export default {
  data() {
    return {
      votes: [],
      voteRecords: [],
      voteForm: {
        id: null,
        name: "",
        voteSum: 0,
      },
      voterName: "",
      selectedVotes: [],
      editing: false,
      modalInstance: null,
      recordsModalInstance: null,
    };
  },
  computed: {
    modalTitle() {
      return this.editing ? "編輯投票項目" : "新增投票項目";
    },
    buttonLabel() {
      return this.editing ? "更新" : "新增";
    },
  },
  methods: {
    showModal(vote) {
      if (vote) {
        this.voteForm = { ...vote };
        this.editing = true;
      } else {
        this.restForm();
        this.editing = false;
      }
      if (!this.modalInstance) {
        this.modalInstance = new Modal(document.getElementById("voteModal"));
      }
      this.modalInstance.show();
    },
    showRecordsModal() {
      VoteService.getVoteRecords()
        .then((response) => {
          this.voteRecords = response.data.sort((a, b) => b.id - a.id); // 按ID降序排序
          if (!this.recordsModalInstance) {
            this.recordsModalInstance = new Modal(
              document.getElementById("recordsModal")
            );
          }
          this.recordsModalInstance.show();
        })
        .catch((error) => {
          console.error("Error loading vote records:", error);
        });
    },
    saveVote() {
      const action = this.editing ? "updateVote" : "addVote";
      VoteService[action](this.voteForm).then(() => {
        this.loadVotes();
        this.modalInstance.hide();
      });
    },
    loadVotes() {
      VoteService.getVotes()
        .then((response) => {
          this.votes = response.data;
        })
        .catch((error) => {
          console.error("Error loading votes:", error);
        });
    },
    deleteVote(id) {
      VoteService.deleteVote(id).then(this.loadVotes);
    },
    restForm() {
      this.voteForm = {
        id: null,
        name: "",
        voteSum: 0,
      };
      this.editing = false;
    },
    toggleVoteSelection(vote) {
      const index = this.selectedVotes.indexOf(vote.id);
      if (index > -1) {
        this.selectedVotes.splice(index, 1);
      } else {
        this.selectedVotes.push(vote.id);
      }
    },
    submitVotes() {
      if (!this.voterName) {
        alert("請輸入姓名");
        return;
      }

      VoteService.voting(this.voterName, this.selectedVotes)
        .then(() => {
          alert("已投票");
          this.selectedVotes = [];
          this.voterName = "";
          this.loadVotes();
        })
        .catch((error) => {
          console.error("Error submitting votes:", error);
        });
    },
  },
  mounted() {
    this.loadVotes();
  },
};
</script>

<template>
  <div class="container mt-3">
    <div class="d-flex justify-content-between mb-3">
      <button @click="showModal(null)" class="btn btn-primary">
        新增投票項目
      </button>
    </div>
    <table class="table table-striped mt-3">
      <thead>
        <tr>
          <th>選擇</th>
          <th>項目編號</th>
          <th>投票名稱</th>
          <th>累積票數</th>
          <th>操作</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="vote in votes" :key="vote.id">
          <td>
            <div class="form-check">
              <input
                class="form-check-input"
                type="checkbox"
                :value="vote.id"
                @change="toggleVoteSelection(vote)"
                :checked="selectedVotes.includes(vote.id)"
              />
            </div>
          </td>
          <td>{{ vote.id }}</td>
          <td>{{ vote.name }}</td>
          <td>{{ vote.votes_num }}</td>
          <td>
            <button @click="showModal(vote)" class="btn btn-warning me-2">
              編輯
            </button>
            <button @click="deleteVote(vote.id)" class="btn btn-danger">
              刪除
            </button>
          </td>
        </tr>
      </tbody>
    </table>
    <div class="d-flex justify-content-between align-items-center mt-3">
      <div class="d-flex align-items-center">
        <input
          type="text"
          class="form-control me-2"
          v-model="voterName"
          placeholder="請輸入姓名"
          required
          style="width: auto"
        />
        <button @click="submitVotes" class="btn btn-success">投票</button>
      </div>
      <button @click="showRecordsModal" class="btn btn-secondary">
        查看投票紀錄
      </button>
    </div>

    <div class="modal fade" id="voteModal" tabindex="-1" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">{{ modalTitle }}</h5>
            <button
              class="btn-close"
              type="button"
              data-bs-dismiss="modal"
            ></button>
          </div>
          <div class="modal-body">
            <form @submit.prevent="saveVote">
              <div class="mb-3">
                <label class="form-label">項目名稱</label>
                <input
                  type="text"
                  class="form-control"
                  v-model="voteForm.name"
                  required
                />
              </div>
              <button type="submit" class="btn btn-primary">
                {{ buttonLabel }}
              </button>
            </form>
          </div>
        </div>
      </div>
    </div>

    <div class="modal fade" id="recordsModal" tabindex="-1" aria-hidden="true">
      <div class="modal-dialog modal-lg">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">投票紀錄</h5>
            <button
              class="btn-close"
              type="button"
              data-bs-dismiss="modal"
            ></button>
          </div>
          <div class="modal-body" style="max-height: 400px; overflow-y: auto">
            <table class="table table-striped">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>投票者姓名</th>
                  <th>項目編號</th>
                  <th>投票項目</th>
                  <th>累積票數</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="record in voteRecords" :key="record.id">
                  <td>{{ record.id }}</td>
                  <td>{{ record.voterName }}</td>
                  <td>{{ record.voteItem.id }}</td>
                  <td>{{ record.voteItem.name }}</td>
                  <td>{{ record.voteItem.votes_num }}</td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
