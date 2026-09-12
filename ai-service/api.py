from pathlib import Path
from pydantic import BaseModel
from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
from faq import FAQS, find_answer


class ChatRequest(BaseModel):
    message: str
    history: list = []
    top_k: int = 4


app = FastAPI(title="HTX Simple AI")
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_methods=["*"],
    allow_headers=["*"],
)

FAQ = Path(__file__).with_name("htx_faq.md").read_text(encoding="utf-8")


@app.post("/chat")
def chat(payload: ChatRequest):
    answer, topic = find_answer(payload.message)
    return {"answer": answer, "sources": ["htx_faq.md", topic], "matched_context": FAQ[:500]}


@app.get("/faq")
def faq_list():
    return [{"question": faq["question"], "topic": faq["topic"]} for faq in FAQS]
